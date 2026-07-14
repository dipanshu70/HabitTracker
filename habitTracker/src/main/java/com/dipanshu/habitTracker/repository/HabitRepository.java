package com.dipanshu.habitTracker.repository;

import com.dipanshu.habitTracker.model.Habit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabitRepository extends MongoRepository<Habit, String> {



    void deleteByIdAndUserId(String id, String userid);
    Habit findByIdAndUserId(String id, String userid);

    List<Habit> findByUserId(String userid);


}