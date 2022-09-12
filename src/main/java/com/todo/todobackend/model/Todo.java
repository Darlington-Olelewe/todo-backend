package com.todo.todobackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Todo extends Audit {
    private String title;
    private String detail;
    private Status status;
    private LocalDate startDate;
    private LocalDate dueDate;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
}
