package com.example.educonnect.service;

import com.example.educonnect.dto.UserRequestDto;
import com.example.educonnect.dto.UserResponseDto;
import com.example.educonnect.entity.User;
import com.example.educonnect.entity.enums.UserRole;
import com.example.educonnect.repository.UserRepository;
import com.example.educonnect.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    @Transactional
    public UserResponseDto registerUser(UserRequestDto userRequestDto){
        User user = modelMapper.map(userRequestDto, User.class);
        user.setRole(UserRole.USER_ROLE);
        user.setPasswordHash(passwordEncoder.encode(userRequestDto.getPasswordHash()));
        User saved = userRepository.save(user);
        return modelMapper.map(saved, UserResponseDto.class);
    }
}
