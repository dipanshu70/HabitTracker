package com.dipanshu.habitTracker.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "habitcompletions")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class HabitCompletion {
        @Id
        private String id;
        private String habitId;
        private String userId;
        private LocalDate completedDate;
        private LocalDate incompletedDate;
        private String message;
        private boolean status;
//        private int frequency;
    }

