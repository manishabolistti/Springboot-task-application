package com.learning.hello.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.hello.entity.TaskList;

public interface TaskListRepository extends JpaRepository<TaskList, Long>{

    List<TaskList> findAllByUserListId(long userid);
    
}
