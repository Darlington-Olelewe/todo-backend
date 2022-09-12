package com.todo.todobackend.service;

import com.todo.todobackend.dto.PersonDto;
import com.todo.todobackend.dto.StatusDto;
import com.todo.todobackend.dto.TodoDto;
import com.todo.todobackend.model.Person;
import com.todo.todobackend.model.Todo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TodoService {
    ResponseEntity<Todo> createTodo(TodoDto dto);

    ResponseEntity<Todo> updateStatus(Long id, StatusDto dto);
    ResponseEntity<Person> createUser(PersonDto dto);


    ResponseEntity<List<Todo>> fetchAll();

    ResponseEntity<List<Todo>> fetchAllPending();

    ResponseEntity<List<Todo>> fetchAllComplete();

    ResponseEntity<List<Todo>> fetchAllProgress();
}
