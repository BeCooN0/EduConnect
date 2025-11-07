package com.example.educonnect.service;

import com.example.educonnect.dto.AuthRequestDto;
import com.example.educonnect.dto.AuthResponseDto;
import com.example.educonnect.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthResponseDto login(AuthRequestDto authRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDto.getEmail(),
                        authRequestDto.getPassword()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequestDto.getEmail());

        String email = userDetails.getUsername();
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("USER");

        String tenantId = authRequestDto.getTenantId() == null
                ? null
                : authRequestDto.getTenantId().trim();

        String accessToken = jwtService.generateAccessToken(email, role, tenantId);
        String refreshToken = jwtService.generateRefreshToken(email);

        return AuthResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(email)
                .role(role)
                .tenantId(tenantId)
                .build();
    }
}
