package com.todo.todobackend.service;

import com.todo.todobackend.dto.LogIn;
import com.todo.todobackend.dto.LoggedInUser;
import org.springframework.http.ResponseEntity;

public interface LoginService {
    ResponseEntity<LoggedInUser> login(LogIn logIn);
}
