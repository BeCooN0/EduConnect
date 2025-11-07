package com.example.educonnect.util;

import com.example.educonnect.security.TenantContext;
import com.example.educonnect.service.CustomUserDetailsServiceImpl;
import com.example.educonnect.security.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final CustomUserDetailsServiceImpl customUserDetailsService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = parseJwt(request);
        String tenantId = request.getHeader("X-Tenant-ID");

        // TenantContext ყოველთვის დავაყენოთ
        if (StringUtils.hasText(tenantId)) {
            TenantContext.setTenantId(tenantId);
        }

        try {
            if (jwt != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                String username = jwtService.getUsername(jwt);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    // tenant ამოიღე JWT-დან, თუ header-ში არაა
                    if (!StringUtils.hasText(tenantId)) {
                        tenantId = jwtService.getTenantId(jwt);
                        if (StringUtils.hasText(tenantId)) {
                            TenantContext.setTenantId(tenantId);
                        }
                        log.debug("Tenant ID resolved from JWT: {}", tenantId != null ? tenantId : "none");
                    }
                }
            }

            filterChain.doFilter(request, response);

        } catch (JwtException e) {
            log.warn("JWT processing failed: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Invalid or expired token\"}");
            return;
        } finally {
            if (!isAsyncDispatch(request)) {
                TenantContext.clear();
            }
        }
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
