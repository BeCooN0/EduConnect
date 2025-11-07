package com.example.educonnect.service;

import com.example.educonnect.dto.UserRequestDto;
import com.example.educonnect.dto.UserResponseDto;
import com.example.educonnect.entity.User;
import com.example.educonnect.entity.enums.UserRole;
import com.example.educonnect.mapper.UserMapper;
import com.example.educonnect.repository.UserRepository;
import com.example.educonnect.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    @Transactional
    public UserResponseDto registerUser(UserRequestDto userRequestDto){
        User user = userMapper.toUser(userRequestDto);
        user.setRole(UserRole.STUDENT);
        user.setPasswordHash(passwordEncoder.encode(userRequestDto.getPasswordHash()));
        User saved = userRepository.save(user);
        return userMapper.toUserDto(saved);
    }
    @Transactional
    public UserResponseDto updateUser(UserRequestDto userRequestDto, User user){
        User updatedUser = new User();
        if (!userRequestDto.getPasswordHash().equalsIgnoreCase(user.getPasswordHash())){
            updatedUser.setPasswordHash(passwordEncoder.encode(userRequestDto.getPasswordHash()));
        }

        userMapper.updateUserFromDto(userRequestDto, user);
    }
}
