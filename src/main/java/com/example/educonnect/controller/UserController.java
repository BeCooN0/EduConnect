package com.example.educonnect.controller;

import com.example.educonnect.dto.UserRequestDto;
import com.example.educonnect.dto.UserResponseDto;
import com.example.educonnect.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRequestDto userRequestDto){
        UserResponseDto userResponseDto = userService.registerUser(userRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/id")
                .buildAndExpand(userResponseDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(userResponseDto);
    }
}