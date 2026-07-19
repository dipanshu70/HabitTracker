package com.dipanshu.habitTracker.controller;

import com.dipanshu.habitTracker.model.HabitCompletion;
import com.dipanshu.habitTracker.service.HabitCompletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/habitcompletion")
@CrossOrigin(origins = "http://localhost:5173")

public class HabitCompletionController {

    @Autowired
    private HabitCompletionService habitCompletionService;

    // POST /habitcompletion/mark-complete/{habitid}
    @PostMapping("/mark-complete/{habitid}")
    public ResponseEntity<HabitCompletion> markComplete(
            @PathVariable String habitid,
            @RequestParam(required = false, defaultValue = "") String message,
            Authentication authentication) {

        String userId = authentication.getName();
        HabitCompletion completion = habitCompletionService.markComplete(habitid, userId, message);
        return ResponseEntity.ok(completion);
    }

    // POST /habitcompletion/mark-incomplete/{habitid}
    @PostMapping("/mark-incomplete/{habitid}")
    public ResponseEntity<HabitCompletion> markIncomplete(
            @PathVariable String habitid,
            @RequestParam(required = false, defaultValue = "") String message,
            Authentication authentication) {

        String userId = authentication.getName();
        HabitCompletion incompletion = habitCompletionService.markIncomplete(habitid, userId, message);
        return ResponseEntity.ok(incompletion);
    }

    // GET /habitcompletion/completed-dates/{habitid}
    @GetMapping("/completed-dates/{habitid}")
    public ResponseEntity<List<LocalDate>> getCompletedDates(
            @PathVariable String habitid,
            Authentication authentication) {

        String userId = authentication.getName();
        List<LocalDate> dates = habitCompletionService.getCompletedDates(habitid, userId);
        return ResponseEntity.ok(dates);
    }

    // GET /habitcompletion/not-completed-dates/{habitid}
    @GetMapping("/not-completed-dates/{habitid}")
    public ResponseEntity<List<LocalDate>> getNotCompletedDates(
            @PathVariable String habitid,
            Authentication authentication) {

        String userId = authentication.getName();
        List<LocalDate> dates = habitCompletionService.getNotCompletedDates(habitid, userId);
        return ResponseEntity.ok(dates);
    }

    // GET /habitcompletion/completion-count/{habitid}
    @GetMapping("/completion-count/{habitid}")
    public ResponseEntity<Integer> getCompletionCount(
            @PathVariable String habitid,
            Authentication authentication) {

        String userId = authentication.getName();
        int count = habitCompletionService.getNoOfCompletion(habitid, userId);
        return ResponseEntity.ok(count);
    }

    // GET /habitcompletion/incompletion-count/{habitid}
    @GetMapping("/incompletion-count/{habitid}")
    public ResponseEntity<Integer> getIncompletionCount(
            @PathVariable String habitid,
            Authentication authentication) {

        String userId = authentication.getName();
        int count = habitCompletionService.getNoOfIncompletion(habitid, userId);
        return ResponseEntity.ok(count);
    }

    // GET /habitcompletion/streak/{habitid}
    @GetMapping("/streak/{habitid}")
    public ResponseEntity<Integer> getStreak(
            @PathVariable String habitid,
            Authentication authentication) {

        String userId = authentication.getName();
        int streak = habitCompletionService.calculateStreak(habitid, userId);
        return ResponseEntity.ok(streak);
    }

    // GET /habitcompletion/history/{habitid}
    @GetMapping("/history/{habitid}")
    public ResponseEntity<List<HabitCompletion>> getHistory(
            @PathVariable String habitid,
            Authentication authentication) {

        String userId = authentication.getName();
        List<HabitCompletion> history = habitCompletionService.getAllCompletions(habitid, userId);
        return ResponseEntity.ok(history);
    }

    // GET /habitcompletion/completed-today/{habitid}
    @GetMapping("/completed-today/{habitid}")
    public ResponseEntity<Boolean> isCompletedToday(
            @PathVariable String habitid,
            Authentication authentication) {

        String userId = authentication.getName();
        boolean completed = habitCompletionService.isCompletedToday(habitid, userId);
        return ResponseEntity.ok(completed);
    }

    // PUT /habitcompletion/update/{completionid}
    @PutMapping("/update/{completionid}")
    public ResponseEntity<HabitCompletion> updateCompletion(
            @PathVariable String completionid,
            @RequestParam(required = false) String message,
            @RequestParam(required = false) Boolean status,
            Authentication authentication) {

        String userId = authentication.getName();
        HabitCompletion updated = habitCompletionService.updateCompletion(completionid, userId, message, status);
        return ResponseEntity.ok(updated);
    }
}