package com.example.educonnect.controller;

import com.example.educonnect.dto.UserRequestDto;
import com.example.educonnect.dto.UserResponseDto;
import com.example.educonnect.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponseDto> updateCurrentUser(
            @RequestBody UserRequestDto userRequestDto,
            @AuthenticationPrincipal UserDetails principal) {

        String username = principal.getUsername();
        UserResponseDto updatedUser = userService.updateUser(userRequestDto, username);

        return ResponseEntity.ok(updatedUser);
    }

     @GetMapping("/me")
     @PreAuthorize("isAuthenticated()")
     public ResponseEntity<UserResponseDto> getCurrentUser(@AuthenticationPrincipal UserDetails principal) {
         return ResponseEntity.ok(userService.getUserByUsername(principal.getUsername()));
     }
}