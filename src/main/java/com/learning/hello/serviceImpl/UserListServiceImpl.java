package com.learning.hello.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.learning.hello.entity.UsersList;
import com.learning.hello.payload.UserListDto;
import com.learning.hello.repository.UserListRepository;
import com.learning.hello.service.UserListService;

@Service
public class UserListServiceImpl implements UserListService {

    @Autowired
    private UserListRepository userListRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserListDto createUserListDto(UserListDto userListDto) {
        // TODO Auto-generated method stub
        userListDto.setPassword(passwordEncoder.encode(userListDto.getPassword()));
        UsersList usersList= userDtoToEntityList(userListDto);
        UsersList saveduser= userListRepository.save(usersList);
        return userEntityToDto(saveduser);
    }

    private UsersList userDtoToEntityList(UserListDto userListDto){
        UsersList users= new UsersList();
        users.setName(userListDto.getName());
        users.setEmail(userListDto.getEmail());
        users.setPassword(userListDto.getPassword());
        return users;
    }

    private UserListDto userEntityToDto(UsersList savedUsersList){
        UserListDto userListDto= new UserListDto();
        userListDto.setId(savedUsersList.getId());
        userListDto.setName(savedUsersList.getName());
        userListDto.setEmail(savedUsersList.getEmail());
        userListDto.setPassword(savedUsersList.getPassword());
        return userListDto;
    }    
}
