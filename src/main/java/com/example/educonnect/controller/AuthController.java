package com.example.educonnect.controller;

import com.example.educonnect.dto.AuthRequestDto;
import com.example.educonnect.dto.AuthResponseDto;
import com.example.educonnect.dto.RefreshRequestDto;
import com.example.educonnect.entity.AuthRefreshToken;
import com.example.educonnect.entity.User;
import com.example.educonnect.entity.enums.UserRole;
import com.example.educonnect.repository.AuthRefreshTokenRepository;
import com.example.educonnect.repository.UserRepository;
import com.example.educonnect.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthRefreshTokenRepository tokenRepository;

    @PostMapping("/register")
    public void register(@RequestBody AuthRequestDto dto) {
        User u = new User();
        u.setEmail(dto.getEmail());
        u.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        u.setRole(UserRole.STUDENT);
        userRepository.save(u);
    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody AuthRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }

        String tenant = dto.getTenantId() != null ? dto.getTenantId() : "public";
        String access = jwtService.generateAccessToken(user.getEmail(), user.getRole().name(), tenant);
        String refresh = jwtService.generateRefreshToken(user.getEmail());

        AuthRefreshToken t = new AuthRefreshToken();
        t.setUserId(user.getId());
        t.setToken(refresh);
        tokenRepository.save(t);

        AuthResponseDto res = new AuthResponseDto();
        res.setAccessToken(access);
        res.setRefreshToken(refresh);
        return res;
    }

    @PostMapping("/refresh")
    public AuthResponseDto refresh(@RequestBody RefreshRequestDto dto) {
        var token = tokenRepository.findByToken(dto.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        var user = userRepository.findById(token.getUserId()).orElseThrow();
        var claims = jwtService.parseToken(dto.getRefreshToken());

        if (claims.getExpiration().before(new java.util.Date())) {
            throw new RuntimeException("Refresh expired");
        }

        String newAccess = jwtService.generateAccessToken(user.getEmail(), user.getRole().name(), "public");
        AuthResponseDto res = new AuthResponseDto();
        res.setAccessToken(newAccess);
        res.setRefreshToken(dto.getRefreshToken());
        return res;
    }
}
