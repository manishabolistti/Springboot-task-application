package com.learning.hello.service;

import java.util.List;

import com.learning.hello.payload.TaskListDto;

public interface TaskListService {
    
    public TaskListDto SaveTask(long userid, TaskListDto taskListDto);

    public List<TaskListDto> getAllTaskList(long userid);

    public TaskListDto getTaskList(long userid, long taskid);

    public void deleteTask(long userid,long taskid);
    

}
