package com.example.educonnect.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.password}")
    private String password;
    private SecretKey key;
    @Value("${jwt.expiration}")
    private long expiration;
    @PostConstruct
    private void init(){
        this.key = Keys.hmacShaKeyFor(password.getBytes());
    }
    public String generateToken(UserDetails userDetails, Map<String, Object> claims){
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .signWith(key)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }
    public Claims getAllClaims(String token ){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public<T> T getClaims(String token, Function<Claims, T> claimsFunction){
        Claims allClaims = getAllClaims(token);
        return claimsFunction.apply(allClaims);
    }

    public String getUsername(String token){
        return getClaims(token, Claims::getSubject);
    }
    public Date getExpiration(String token){
        return getClaims(token, Claims::getExpiration);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
       return getClaims(token, claims -> {
           return claims.getSubject().equals(userDetails.getUsername()) && !claims.getExpiration().before(new Date());
       });
    }
    public String getTenantId(String token){
         return getClaims(token, claims ->
                claims.get("tenantId", String.class));
    }
}
