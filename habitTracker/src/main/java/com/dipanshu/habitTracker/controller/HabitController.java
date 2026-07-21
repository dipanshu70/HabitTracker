package com.dipanshu.habitTracker.controller;

import com.dipanshu.habitTracker.model.Habit;
import com.dipanshu.habitTracker.model.HabitUpdateRequest;
import com.dipanshu.habitTracker.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habit")
@CrossOrigin(origins = "http://localhost:5173")

public class HabitController {

    @Autowired
    private HabitService habitService;

    @PostMapping("/create")
    public ResponseEntity<Habit> createHabit(@RequestBody Habit habit, Authentication authentication) {
        // The token tells us exactly who is making this request
        String userId = authentication.getName();

        Habit newhabit = habitService.addHabit(habit.getHabitName(), habit.getDescription(), userId);
        return ResponseEntity.ok(newhabit);
    }

    // Removed {userid} from the URL!
    @DeleteMapping("/delete/{habitid}")
    public ResponseEntity<Void> deleteHabit(@PathVariable String habitid, Authentication authentication) {
        String userId = authentication.getName();
        habitService.deleteHabit(habitid, userId);
        return ResponseEntity.noContent().build();
    }

    // Removed {userid} from the URL!
    @GetMapping("/allhabit")
    public ResponseEntity<List<Habit>> getAllHabit(Authentication authentication) {
        String userId = authentication.getName();
        List<Habit> habits = habitService.getAllHabit(userId);
        return ResponseEntity.ok(habits);
    }

    // Removed {userid} from the URL!
    @GetMapping("/getone/{id}")
    public ResponseEntity<Habit> getHabitByID(@PathVariable String id, Authentication authentication) {
        String userId = authentication.getName();
        Habit habit = habitService.gethabitbyid(id, userId);
        return ResponseEntity.ok(habit);
    }

    // Removed {userid} from the URL!
    @PutMapping("/update/{id}")
    public ResponseEntity<Habit> updateHabit(@PathVariable String id,
                                             @RequestBody HabitUpdateRequest request,
                                             Authentication authentication) {
        String userId = authentication.getName();
        Habit updatedHabit = habitService.updatehabit(id, userId, request.getHabitname(), request.getDescription());
        return ResponseEntity.ok(updatedHabit);
    }
}