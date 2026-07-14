package com.dipanshu.habitTracker.service;

import com.dipanshu.habitTracker.model.Habit;
import com.dipanshu.habitTracker.model.HabitCompletion;
import com.dipanshu.habitTracker.repository.HabitCompletionRepository;
import com.dipanshu.habitTracker.repository.HabitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HabitCompletionService {
    @Autowired
    private HabitCompletionRepository habitCompletionRepository;
    @Autowired
    private HabitRepository habitRepository;

    // Method to mark habit complete
    public HabitCompletion markComplete(String habitId, String userid, String message) {
        HabitCompletion newCompletion= new HabitCompletion();
        Habit habit = habitRepository.findByIdAndUserId(habitId, userid);
        if(habit == null) {
            throw new IllegalArgumentException("Habit not found or doesn't belong to user");
        }

        newCompletion.setHabitId(habitId);
        newCompletion.setUserId(userid);
        newCompletion.setMessage(message);
        newCompletion.setCompletedDate(LocalDate.now());
        newCompletion.setStatus(true);
        return habitCompletionRepository.save(newCompletion);
    }

    public HabitCompletion markIncomplete(String habitId, String userid, String message){
        HabitCompletion newIncompletion= new HabitCompletion();
        Habit habit = habitRepository.findByIdAndUserId(habitId, userid);
        if(habit == null) {
            throw new IllegalArgumentException("Habit not found or doesn't belong to user");
        }
        newIncompletion.setUserId(userid);
        newIncompletion.setHabitId(habitId);
        newIncompletion.setMessage(message);
        newIncompletion.setIncompletedDate(LocalDate.now());
        newIncompletion.setStatus(false);
        return habitCompletionRepository.save(newIncompletion);
    }

    // Get all COMPLETED dates for a habit
    public List<LocalDate> getCompletedDates(String habitId, String userid) {

        // Get all completed records
        List<HabitCompletion> completedRecords = habitCompletionRepository
                .findByHabitIdAndUserIdAndStatus(habitId, userid, true);

        // Extract just the dates
        return completedRecords.stream()
                .map(HabitCompletion::getCompletedDate)
                .collect(Collectors.toList());
    }

    // Get all NOT-COMPLETED dates for a habit
    public List<LocalDate> getNotCompletedDates(String habitId, String userid) {

        // Get all not-completed records
        List<HabitCompletion> notCompletedRecords = habitCompletionRepository
                .findByHabitIdAndUserIdAndStatus(habitId, userid, false);

        // Extract just the dates
        return notCompletedRecords.stream()
                .map(HabitCompletion::getIncompletedDate)
                .collect(Collectors.toList());
    }

    public int getNoOfCompletion(String habitId,String userid){
        List<LocalDate>completedDate=getCompletedDates(habitId,userid);
        return completedDate.size();
    }
    public int getNoOfIncompletion(String habitId,String userid) {
        List<LocalDate> incompletedDate = getNotCompletedDates(habitId, userid);
        return incompletedDate.size();

    }






    public int calculateStreak(String habitId, String userid) {
        List<LocalDate> completedDates = getCompletedDates(habitId, userid);
        // Guard clause for null or empty lists
        if (completedDates == null || completedDates.isEmpty()) {
            return 0;
        }
        // 1. Remove duplicates and sort descending safely (newest first)
        // This prevents UnsupportedOperationException if the original list is immutable
        List<LocalDate> sortedUniqueDates = completedDates.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        LocalDate today = LocalDate.now();
        LocalDate latestCompletion = sortedUniqueDates.get(0);
        // 2. Check if the streak is already dead
        // If the most recent completion is older than yesterday, the active streak is 0
        if (latestCompletion.isBefore(today.minusDays(1))) {
            return 0;
        }
        int streak = 0;
        // 3. Start counting backwards from the most recent completion (which is either today or yesterday)
        LocalDate expectedDate = latestCompletion;
        for (LocalDate date : sortedUniqueDates) {
            if (date.equals(expectedDate)) {
                streak++;
                expectedDate = expectedDate.minusDays(1); // Move to the previous day
            } else {
                // Found a gap - streak is broken
                break;
            }
        }
        return streak;
    }








    public boolean isCompletedToday(String habitId, String userid) {

        HabitCompletion completion = habitCompletionRepository
                .findByHabitIdAndUserIdAndCompletedDateAndStatus(habitId, userid, LocalDate.now(), true);

        return completion != null;
    }
    public List<HabitCompletion> getAllCompletions(String habitId, String userid) {

        // Verify habit exists
        Habit habit = habitRepository.findByIdAndUserId(habitId, userid);
        if(habit == null) {
            throw new RuntimeException("Habit not found or doesn't belong to user");
        }

        return habitCompletionRepository.findByHabitIdAndUserId(habitId, userid);
    }
    public void deleteCompletionsByHabitId(String habitId) {
        habitCompletionRepository.deleteByHabitId(habitId);
    }

    // Method to update an existing completion record
    public HabitCompletion updateCompletion(String completionId, String userId, String newMessage, Boolean newStatus) {

        // 1. Find the specific completion record by its ID
        HabitCompletion existingCompletion = habitCompletionRepository.findById(completionId)
                .orElseThrow(() -> new IllegalArgumentException("Completion record not found"));

        // 2. Security check: verify it belongs to this user
        if (!existingCompletion.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Not authorized to update this record");
        }

        // 3. Update the message if a new one is provided
        if (newMessage != null) {
            existingCompletion.setMessage(newMessage);
        }

        // 4. Update the status if provided, and align the dates
        if (newStatus != null && existingCompletion.isStatus() != newStatus) {
            existingCompletion.setStatus(newStatus);

            if (newStatus == true) {
                // Switched to Completed
                if (existingCompletion.getIncompletedDate() != null) {
                    existingCompletion.setCompletedDate(existingCompletion.getIncompletedDate());
                } else {
                    existingCompletion.setCompletedDate(LocalDate.now());
                }
                existingCompletion.setIncompletedDate(null);

            } else {
                // Switched to Incomplete
                if (existingCompletion.getCompletedDate() != null) {
                    existingCompletion.setIncompletedDate(existingCompletion.getCompletedDate());
                } else {
                    existingCompletion.setIncompletedDate(LocalDate.now());
                }
                existingCompletion.setCompletedDate(null);
            }
        }

        // 5. Save and return the updated record
        return habitCompletionRepository.save(existingCompletion);
    }

}