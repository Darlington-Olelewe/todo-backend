package com.todo.todobackend.config.security;


import com.todo.todobackend.model.Person;
import com.todo.todobackend.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor @Slf4j
public class PersonDetailService implements UserDetailsService {
    private PersonRepository personRepo;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person person = personRepo.findPersonByEmail(email)


                .orElseThrow(() -> new UsernameNotFoundException("No Staff with " + email+" found"));

        log.info("{}",person);

        List<SimpleGrantedAuthority> permissions = new ArrayList<>();

        permissions.add(new SimpleGrantedAuthority(person.getRole().name()));

        UserDetails userDetails = new User(person.getEmail(),person.getPassword(),permissions);

        log.info("this is the userDetail {}",userDetails);

        return userDetails;
    }

}
