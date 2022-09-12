package com.todo.todobackend.controller;

import com.todo.todobackend.dto.*;
import com.todo.todobackend.model.Person;
import com.todo.todobackend.model.Todo;
import com.todo.todobackend.service.LoginService;
import com.todo.todobackend.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonController {
    private final LoginService loginService;
    private final TodoService todoService;

    @PostMapping("/login")
    public ResponseEntity<LoggedInUser> login(@RequestBody LogIn logIn){
        return loginService.login(logIn);
    }

    @PostMapping("")
    public ResponseEntity<Person> createUser(@RequestBody PersonDto dto){
        return todoService.createUser(dto);
    }

    @PostMapping("/todos")
    public ResponseEntity<Todo> createTodo(@RequestBody TodoDto todoDto){
        return todoService.createTodo(todoDto);
    }

    @PutMapping("/todos/{todoId}")
    public ResponseEntity<Todo> changeStatus(@PathVariable long todoId, @RequestBody StatusDto statusDto){
        return todoService.updateStatus(todoId,statusDto);
    }
    @GetMapping("/todos")
    public ResponseEntity<List<Todo>> fetchAll(){
        return todoService.fetchAll();
    }

    @GetMapping("/todos/pending")
    public ResponseEntity<List<Todo>> fetchAllPending(){
        return todoService.fetchAllPending();
    }
    @GetMapping("/todos/complete")
    public ResponseEntity<List<Todo>> fetchAllComplete(){
        return todoService.fetchAllComplete();
    }
    @GetMapping("/todos/progress")
    public ResponseEntity<List<Todo>> fetchAllProgress(){
        return todoService.fetchAllProgress();
    }


}
