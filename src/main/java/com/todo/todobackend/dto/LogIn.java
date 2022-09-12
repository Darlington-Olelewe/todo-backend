package com.todo.todobackend.dto;

import lombok.*;

import javax.persistence.Entity;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogIn {
    private String email;
    private String password;
}
