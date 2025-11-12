package com.example.educonnect.util;

import com.example.educonnect.entity.Tenant; // <-- DDAAMATA
import com.example.educonnect.repository.TenantRepository;
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
import org.springframework.beans.factory.annotation.Value;
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
    private final TenantRepository tenantRepository;

    @Value("${app.tenant.base-domain}")
    private String baseDomain;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String tenantId = resolveTenantId(request);
            TenantContext.setTenantId(tenantId);

            String jwt = parseJwt(request);

            if (jwt != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                String username = jwtService.getUsername(jwt);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    if (tenantId.equals(TenantContext.PUBLIC)) {
                        String jwtTenant = jwtService.getTenantId(jwt);
                        if (StringUtils.hasText(jwtTenant)) {
                            TenantContext.setTenantId(jwtTenant);
                            log.debug("Tenant ID resolved from JWT: {}", jwtTenant);
                        }
                    }
                }
            }

            filterChain.doFilter(request, response);

        } catch (JwtException e) {
            log.warn("JWT processing failed: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
            return;
        } finally {
            if (!isAsyncDispatch(request)) {
                TenantContext.clear();
            }
        }
    }

    private String resolveTenantId(HttpServletRequest request) {

        String tenantIdFromHeader = request.getHeader("X-Tenant-ID");
        if (StringUtils.hasText(tenantIdFromHeader)) {
            Tenant tenant = tenantRepository.findBySubdomain(tenantIdFromHeader)
                    .orElseThrow(() -> new JwtException("Tenant '" + tenantIdFromHeader + "' not found"));

            if (!tenant.isStatus()) {
                log.warn("Access attempt to inactive tenant via Header: {}", tenantIdFromHeader);
                throw new JwtException("Tenant '" + tenantIdFromHeader + "' is inactive.");
            }

            log.debug("Tenant ID resolved from Header: {}", tenantIdFromHeader);
            return tenantIdFromHeader;
        }

        String serverName = request.getServerName();
        if (StringUtils.hasText(serverName) && serverName.endsWith("." + baseDomain)) {
            String subdomain = serverName.substring(0, serverName.length() - baseDomain.length() - 1);

            Tenant tenant = tenantRepository.findBySubdomain(subdomain)
                    .orElseThrow(() -> new JwtException("Tenant subdomain '" + subdomain + "' not found"));
            if (!tenant.isStatus()) {
                log.warn("Access attempt to inactive tenant via Subdomain: {}", subdomain);
                throw new JwtException("Tenant '" + subdomain + "' is inactive.");
            }

            log.debug("Tenant ID resolved from Subdomain: {}", tenant.getSubdomain());
            return tenant.getSubdomain();
        }
        log.debug("No specific tenant found, resolving to PUBLIC.");
        return TenantContext.PUBLIC;
    }


    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}