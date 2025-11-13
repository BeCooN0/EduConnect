package com.example.educonnect.service;

import com.example.educonnect.dto.UserRequestDto;
import com.example.educonnect.dto.UserResponseDto;
import com.example.educonnect.entity.User;
import com.example.educonnect.entity.enums.UserRole;
import com.example.educonnect.mapper.UserMapper;
import com.example.educonnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserResponseDto registerUser(UserRequestDto userRequestDto){
        User user = userMapper.toUser(userRequestDto);
        user.setRole(UserRole.STUDENT);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        User saved = userRepository.save(user);
        return userMapper.toUserDto(saved);
    }

    @Transactional
    public UserResponseDto updateUser(UserRequestDto userRequestDto, String username){

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        if (userRequestDto.getPassword() != null && !userRequestDto.getPassword().isEmpty()) {
            if (!passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
            }
        }

        userMapper.updateUserFromDto(userRequestDto, user);

        User saved = userRepository.save(user);
        return userMapper.toUserDto(saved);
    }

    public UserResponseDto getUserByUsername(String username) {
        User user = userRepository.findByEmail(username).orElseThrow();
        return userMapper.toUserDto(user);
    }
}