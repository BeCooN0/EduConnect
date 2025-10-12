package com.example.educonnect.service;

import com.example.educonnect.dto.LoginRequestDto;
import com.example.educonnect.dto.LoginResponseDto;
import com.example.educonnect.security.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public LoginResponseDto login(LoginRequestDto loginRequestDto){
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPasswordHash()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDto.getEmail());
        Map<String, Object> claims = new HashMap<>();
        claims.put("tenantId", loginRequestDto.getTenantId());
        return new LoginResponseDto(jwtService.generateToken(userDetails,claims));
    }
}

