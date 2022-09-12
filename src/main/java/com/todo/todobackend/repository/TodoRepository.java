package com.todo.todobackend.repository;

import com.todo.todobackend.model.Person;
import com.todo.todobackend.model.Status;
import com.todo.todobackend.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findTodosByPersonAndStatus(Person p, Status s);
    List<Todo> findTodosByPerson(Person p);
}
