package com.todo.todobackend.service.impl;

import com.todo.todobackend.config.security.JwtTokenProvider;
import com.todo.todobackend.dto.*;
import com.todo.todobackend.model.Person;
import com.todo.todobackend.model.Role;
import com.todo.todobackend.model.Status;
import com.todo.todobackend.model.Todo;
import com.todo.todobackend.repository.PersonRepository;
import com.todo.todobackend.repository.TodoRepository;
import com.todo.todobackend.service.LoginService;
import com.todo.todobackend.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService, TodoService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final HttpServletResponse httpServletResponse;

    private final PersonRepository personRepo;
    private final PasswordEncoder encoder;
    private final TodoRepository todoRepo;
    @Override
    public ResponseEntity<LoggedInUser> login(LogIn login) {
        Authentication authentication;
        String accessToken;
        String refreshToken;
        Person person = null;

        try{
            org.springframework.security.core.Authentication auth =  new UsernamePasswordAuthenticationToken(
                    login.getEmail(),login.getPassword());
            authentication = authenticationManager.authenticate(auth);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            accessToken = jwtTokenProvider.generateTokenAcess(authentication);
            refreshToken = jwtTokenProvider.generateTokenRefresh(authentication);
            httpServletResponse.setHeader("Authorization", accessToken);
            httpServletResponse.setHeader("Authorization-Refresh", refreshToken);

            person = personRepo.findPersonByEmail(login.getEmail()).get();

        }
        catch (BadCredentialsException ex){
            throw new RuntimeException("un able to login");
        }

        LoggedInUser loggedInUser = LoggedInUser.builder()
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .role(person.getRole().name())
                .email(person.getEmail())
                .id(person.getId())
                .token(accessToken)
                .refreshToken(refreshToken)
                .build();
        return ResponseEntity.ok(loggedInUser);
    }

    @Override
    public ResponseEntity<LoggedInUser> refresh() {
        String accessToken;
        String refreshToken;
        Person person = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try{

            SecurityContextHolder.getContext().setAuthentication(authentication);
            accessToken = jwtTokenProvider.generateTokenAcess(authentication);
            refreshToken = jwtTokenProvider.generateTokenRefresh(authentication);
            httpServletResponse.setHeader("Authorization", accessToken);
            UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            person = personRepo.findPersonByEmail(user.getUsername()).get();

        }
        catch (BadCredentialsException ex){
            throw new RuntimeException("un able to login");
        }

        LoggedInUser loggedInUser = LoggedInUser.builder()
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .role(person.getRole().name())
                .email(person.getEmail())
                .id(person.getId())
                .token(accessToken)
                .refreshToken(refreshToken)
                .build();
        return ResponseEntity.ok(loggedInUser);
    }

    @Override
    public ResponseEntity<Todo> createTodo(TodoDto dto) {
        Person p = getPerson();
        Todo todo = Todo.builder()
                .detail(dto.getDetail())
                .dueDate(dto.getDueDate())
                .person(p)
                .startDate(dto.getStartDate())
                .title(dto.getTitle())
                .status(Status.PENDING)
                .build();
        return ResponseEntity.ok(todoRepo.save(todo));
    }

    @Override
    public ResponseEntity<Todo> updateStatus(Long id, StatusDto dto) {
        Todo todo = todoRepo.findById(id).get();
        todo.setStatus(dto.getStatus());
        return ResponseEntity.ok(todoRepo.save(todo));
    }

    @Override
    public ResponseEntity<Person> createUser(PersonDto dto) {
        Person p = Person.builder()
                .password(encoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .role(Role.USER)
                .build();
        return ResponseEntity.ok(personRepo.save(p));
    }

    @Override
    public ResponseEntity<List<Todo>> fetchAll() {
        Person p = getPerson();
        List<Todo> todoList = todoRepo.findTodosByPerson(p);
        return ResponseEntity.ok(todoList);
    }

    @Override
    public ResponseEntity<List<Todo>> fetchAllPending() {
        Person p = getPerson();
        List<Todo> todoList = todoRepo.findTodosByPersonAndStatus(p, Status.PENDING);
        return ResponseEntity.ok(todoList);
    }

    @Override
    public ResponseEntity<List<Todo>> fetchAllComplete() {
        Person p = getPerson();
        List<Todo> todoList = todoRepo.findTodosByPersonAndStatus(p, Status.DONE);
        return ResponseEntity.ok(todoList);
    }

    @Override
    public ResponseEntity<List<Todo>> fetchAllProgress() {
        Person p = getPerson();
        List<Todo> todoList = todoRepo.findTodosByPersonAndStatus(p, Status.INPROGRESS);
        return ResponseEntity.ok(todoList);
    }


    private Person getPerson(){
        UserDetails loggedInUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person p = personRepo.findPersonByEmail(loggedInUser.getUsername()).get();
        return  p;
    }
}
