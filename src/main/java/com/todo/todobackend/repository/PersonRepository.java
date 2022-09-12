package com.todo.todobackend.repository;

import com.todo.todobackend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person,Long> {
    Optional<Person> findPersonByEmail(String email);
}
