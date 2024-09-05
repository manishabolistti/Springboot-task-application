package com.learning.hello.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.hello.payload.JwtAuthResponse;
import com.learning.hello.payload.LoginDto;
import com.learning.hello.payload.UserListDto;
import com.learning.hello.security.JwtTokenProvider;
import com.learning.hello.service.UserListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserListService userListService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<UserListDto> createuser(@RequestBody UserListDto userListDto) {
        return new ResponseEntity<>(userListService.createUserListDto(userListDto),HttpStatus.CREATED) ;
    }
    
    @PostMapping("/login")    
    public ResponseEntity<JwtAuthResponse> loginUser(@RequestBody LoginDto loginDto){
        Authentication authentication= authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );
        System.out.println(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token= jwtTokenProvider.genarateToken(authentication);
        // return new ResponseEntity<>(token,HttpStatus.OK);
        return ResponseEntity.ok(new JwtAuthResponse(token));
    }
    
}
