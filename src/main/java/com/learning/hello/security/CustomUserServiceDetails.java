package com.learning.hello.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.learning.hello.entity.UsersList;
import com.learning.hello.exception.UserNotFoundException;
import com.learning.hello.repository.UserListRepository;

@Service
public class CustomUserServiceDetails implements UserDetailsService {

    @Autowired
    private UserListRepository userListRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    
       UsersList usersList= userListRepository.findByEmail(email).orElseThrow(
        ()-> new UserNotFoundException(String.format("User with email: %s is not found", email)));
        Set<String> roles= new HashSet<>();
        roles.add("ROLE_ADMIN");
        return new User(usersList.getEmail(), usersList.getPassword(),userAuthorities(roles) );
    }

    private Collection<? extends GrantedAuthority> userAuthorities(Set<String> roles){
        return roles.stream().map(
            role -> new SimpleGrantedAuthority(role)
        ).collect(Collectors.toList());
    }
    
}
