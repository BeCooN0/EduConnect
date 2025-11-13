package com.example.educonnect.controller;

import com.example.educonnect.dto.AuthRequestDto;
import com.example.educonnect.dto.AuthResponseDto;
import com.example.educonnect.dto.RefreshRequestDto;
import com.example.educonnect.dto.UserRequestDto;
import com.example.educonnect.dto.UserResponseDto;
import com.example.educonnect.service.AuthService;
import com.example.educonnect.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto userRequestDto) {

        UserResponseDto registeredUser = userService.registerUser(userRequestDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/id")
                .buildAndExpand(registeredUser.getId())
                .toUri();

        return ResponseEntity.created(location).body(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto dto) {
        AuthResponseDto login = authService.login(dto);
        return ResponseEntity.ok(login);
    }


    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDto> refresh(@RequestBody RefreshRequestDto dto) {
        AuthResponseDto refresh = authService.refresh(dto);
        return ResponseEntity.ok(refresh);
    }
}