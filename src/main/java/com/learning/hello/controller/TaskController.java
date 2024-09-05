package com.learning.hello.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.hello.payload.TaskListDto;
import com.learning.hello.service.TaskListService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api")
public class TaskController {
    
    @Autowired
    private TaskListService taskListService;
    //save task
    @PostMapping("/{userid}/tasks")
    public ResponseEntity<TaskListDto> saveTask(@PathVariable(name="userid") long userid, @RequestBody TaskListDto taskListDto) {
        //TODO: process POST request
        
       return new ResponseEntity<>(taskListService.SaveTask(userid, taskListDto), HttpStatus.CREATED);
    }

    @GetMapping("/{userid}/tasks")
    public ResponseEntity<List<TaskListDto>> getAllTask(@PathVariable(name = "userid") long userid) {
        return new ResponseEntity<>(taskListService.getAllTaskList(userid), HttpStatus.OK);
    }

    //individual Task
    @GetMapping("/{userid}/task/{taskid}")
    public ResponseEntity<TaskListDto> getTask(@PathVariable(name="userid") long userid,
    @PathVariable(name = "taskid") long taskid
    ){
        return new ResponseEntity<>(taskListService.getTaskList(userid, taskid),HttpStatus.OK);
    }

    @DeleteMapping("/{userid}/task/{taskid}")
    public ResponseEntity<String> deleteTask(@PathVariable(name="userid") long userid,
    @PathVariable(name = "taskid") long taskid
    ){
        taskListService.deleteTask(userid, taskid);
        return new ResponseEntity<>("user deleted successfully",HttpStatus.OK);
    }
    

}
