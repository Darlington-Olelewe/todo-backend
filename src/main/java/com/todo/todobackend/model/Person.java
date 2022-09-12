package com.todo.todobackend.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person extends Audit{
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    private Role role;
}
