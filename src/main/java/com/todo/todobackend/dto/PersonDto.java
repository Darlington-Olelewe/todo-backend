package com.todo.todobackend.dto;

import com.todo.todobackend.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PersonDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
