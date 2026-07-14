package com.dipanshu.habitTracker.service;

import com.dipanshu.habitTracker.model.Habit;
import com.dipanshu.habitTracker.model.User;
import com.dipanshu.habitTracker.repository.HabitRepository;
import com.dipanshu.habitTracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HabitService {

    @Autowired
    private HabitRepository habitRepository;

    public Habit addHabit(String habitname, String description, String userid) {
        Habit habit = new Habit();
        habit.setUserId(userid);
        habit.setHabitName(habitname);
        habit.setCreatedAt(LocalDate.now());
        habit.setDescription(description);
        return habitRepository.save(habit);
    }

    public void deleteHabit(String id, String userid) {
        // Because we enforce the userid check here, they can't delete other people's habits
        habitRepository.deleteByIdAndUserId(id, userid);
    }

    public Habit gethabitbyid(String id, String userid) {
        Habit habit = habitRepository.findByIdAndUserId(id, userid);
        if(habit == null) {
            throw new RuntimeException("Habit not found or doesn't belong to user");
        }
        return habit;
    }

    public Habit updatehabit(String id, String userid, String habitname, String description) {
        Habit habit = habitRepository.findByIdAndUserId(id, userid);
        if(habit == null) {
            throw new RuntimeException("Habit not found or doesn't belong to user");
        }
        habit.setHabitName(habitname);
        habit.setDescription(description);
        return habitRepository.save(habit);
    }

    public List<Habit> getAllHabit(String userid) {
        // This is efficient and secure
        return habitRepository.findByUserId(userid);
    }
}