package com.dipanshu.habitTracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                              // Auto generates getters/setters
@NoArgsConstructor                 // Auto generates empty constructor
@AllArgsConstructor
public class HabitUpdateRequest {
    private String habitname;
    private String description;

}
