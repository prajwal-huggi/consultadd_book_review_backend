package com.example.consultadd_mini_project.service;

import com.example.consultadd_mini_project.Repository.UserRepo;
import com.example.consultadd_mini_project.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Autowired
    UserRepo repo;

    @Value(("${jwt.secret}"))
    private String secretKey;

    public String generateToken(String email){
        User.Roles role= repo.findByEmail(email).getRole();

        System.out.println("The role of the user is "+ role);

        Map<String, Object> claims= new HashMap<>();
        claims.put("role", role);

        return Jwts
                .builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ 1000* 60 * 60))
                .signWith(getKey())
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String email= extractEmail(token);

        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    private SecretKey getKey(){
        byte[] keyByte= Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(keyByte);
    }

    public String extractRole(String token){
        return extractAllClaims(token).get("role", String.class);
    }
    public String extractEmail(String token){
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimResolver){
        final Claims claims= extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }
}
