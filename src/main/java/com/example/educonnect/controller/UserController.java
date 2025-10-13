package com.example.educonnect.controller;

import com.example.educonnect.dto.LoginRequestDto;
import com.example.educonnect.dto.LoginResponseDto;
import com.example.educonnect.dto.UserRequestDto;
import com.example.educonnect.dto.UserResponseDto;
import com.example.educonnect.service.AuthService;
import com.example.educonnect.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@Validated @RequestBody UserRequestDto userRequestDto){
        UserResponseDto userResponseDto = userService.registerUser(userRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/id")
                .buildAndExpand(userResponseDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(userResponseDto);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Validated @RequestBody LoginRequestDto loginRequestDto){
        LoginResponseDto login = authService.login(loginRequestDto);
        return new ResponseEntity<>(login, HttpStatus.OK);
    }
}