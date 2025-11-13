package com.example.educonnect.service;

import com.example.educonnect.dto.AuthRequestDto;
import com.example.educonnect.dto.AuthResponseDto;
import com.example.educonnect.dto.RefreshRequestDto;
import com.example.educonnect.repository.AuthRefreshTokenRepository;
import com.example.educonnect.repository.UserRepository;
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

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthRefreshTokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

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

    public AuthResponseDto refresh(RefreshRequestDto dto) {
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
