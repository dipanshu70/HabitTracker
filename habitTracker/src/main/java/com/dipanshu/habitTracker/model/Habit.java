package com.dipanshu.habitTracker.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "habits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Habit {

    @Id
    private String id;
    private String userId;
    private String habitName;
    private String description;
    private LocalDate createdAt;


}
