package com.dipanshu.habitTracker.repository;

import com.dipanshu.habitTracker.model.HabitCompletion;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface HabitCompletionRepository extends MongoRepository<HabitCompletion, String> {

    boolean existsByHabitIdAndUserId(String habitId, String userid);

    HabitCompletion findByHabitIdAndUserIdAndCompletedDateAndStatus(String habitId, String userid, LocalDate date, boolean status);

    List<HabitCompletion> findByHabitIdAndUserIdAndStatus(String habitId, String userid, boolean status);

    List<HabitCompletion> findByHabitIdAndUserId(String habitId, String userid);


    void deleteByHabitId(String habitId);
}