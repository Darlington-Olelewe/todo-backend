package com.todo.todobackend.dto;

import com.todo.todobackend.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoDto {
    private String title;
    private String detail;
    private Status status;
    private LocalDate startDate;
    private LocalDate dueDate;
}
