package com.example.educonnect.service;

import com.example.educonnect.dto.UserRequestDto;
import com.example.educonnect.dto.UserResponseDto;
import com.example.educonnect.entity.User;
import com.example.educonnect.entity.enums.UserRole;
import com.example.educonnect.mapper.UserMapper;
import com.example.educonnect.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserService userService;

    @Test
    void shouldRegisterUserSuccessfully() {
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setEmail("test@edu.com");
        requestDto.setPassword("secretPassword");

        User userEntity = new User();
        userEntity.setEmail(requestDto.getEmail());

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail(requestDto.getEmail());
        savedUser.setRole(UserRole.STUDENT);

        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(1L);
        responseDto.setEmail(requestDto.getEmail());

        when(userMapper.toUser(requestDto)).thenReturn(userEntity);
        when(passwordEncoder.encode("secretPassword")).thenReturn("hashed_secret_password");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userMapper.toUserDto(savedUser)).thenReturn(responseDto);

        UserResponseDto result = userService.registerUser(requestDto);

        assertNotNull(result);
        assertEquals(requestDto.getEmail(), result.getEmail());
        assertEquals(1L, result.getId());

        verify(passwordEncoder).encode("secretPassword");
        verify(userRepository).save(any(User.class));
    }
}