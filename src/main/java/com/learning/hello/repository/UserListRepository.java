package com.learning.hello.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.hello.entity.UsersList;

public interface UserListRepository extends JpaRepository<UsersList, Long> {

   Optional<UsersList> findByEmail(String email);
    
}
