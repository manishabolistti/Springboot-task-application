package com.learning.hello.serviceImpl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.hello.entity.TaskList;
import com.learning.hello.entity.UsersList;
import com.learning.hello.exception.ApiException;
import com.learning.hello.exception.TaskNotFounfException;
import com.learning.hello.exception.UserNotFoundException;
import com.learning.hello.payload.TaskListDto;
import com.learning.hello.repository.TaskListRepository;
import com.learning.hello.repository.UserListRepository;
import com.learning.hello.service.TaskListService;

@Service
public class TaskListServiceImpl implements TaskListService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserListRepository userListRepository;

    @Autowired
    private TaskListRepository taskListRepository;

    @Override
    public TaskListDto SaveTask(long userid, TaskListDto taskListDto) {
        // TODO Auto-generated method stub
        UsersList user= userListRepository.findById(userid).orElseThrow(
            ()-> new UserNotFoundException(String.format("userid %d not found ", userid))
            );
        TaskList taskList = modelMapper.map(taskListDto, TaskList.class);
        taskList.setUserList(user);
        TaskList savedTaskList= taskListRepository.save(taskList);
        return modelMapper.map(savedTaskList, TaskListDto.class);
    }

    @Override
    public List<TaskListDto> getAllTaskList(long userid) {
        // TODO Auto-generated method stub
        userListRepository.findById(userid).orElseThrow(()-> new UserNotFoundException(String.format("userid %d not found ", userid)));
        List<TaskList> tasks = taskListRepository.findAllByUserListId(userid);
        return tasks.stream().map(
            task -> modelMapper.map(task, TaskListDto.class)
            ).collect(Collectors.toList());
        
    }

    @Override
    public TaskListDto getTaskList(long userid, long taskid) {
       UsersList usersList= userListRepository.findById(userid).orElseThrow(()-> new UserNotFoundException(String.format("userid %d not found ", userid)));
        TaskList taskList=taskListRepository.findById(taskid).orElseThrow(()-> new TaskNotFounfException(String.format("Task %d not found", taskid)));

        if(usersList.getId() != taskList.getUserList().getId()){
            throw new ApiException(String.format("Tash Id %d is not belongs to userId %d", taskid,userid));
        }
        // TODO Auto-generated method stub
        return modelMapper.map(taskList, TaskListDto.class);
    }

    @Override
    public void deleteTask(long userid, long taskid) {
        // TODO Auto-generated method stub
        UsersList usersList= userListRepository.findById(userid).orElseThrow(()-> new UserNotFoundException(String.format("userid %d not found ", userid)));
        TaskList taskList=taskListRepository.findById(taskid).orElseThrow(()-> new TaskNotFounfException(String.format("Task %d not found", taskid)));

        if(usersList.getId() != taskList.getUserList().getId()){
            throw new ApiException(String.format("Tash Id %d is not belongs to userId %d", taskid,userid));
        }
        taskListRepository.deleteById(taskid);
    }
    
}
