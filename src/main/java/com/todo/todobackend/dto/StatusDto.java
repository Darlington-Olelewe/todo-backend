package com.todo.todobackend.dto;

import com.todo.todobackend.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class StatusDto {
    private Status status;
}
