package com.todo.todobackend.dto;

import lombok.*;

import javax.persistence.Entity;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoggedInUser {
    private Long id;
    private String firstName;
    private String lastName;
    private String token;
    private String role;
    private String email;
}
