package com.example.consultadd_mini_project.config;

import com.example.consultadd_mini_project.service.JwtService;
import com.example.consultadd_mini_project.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JwtService jwtService;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader= request.getHeader("Authorization");
        String token= null;
        String email= null;

        if(authHeader!= null && authHeader.startsWith("Bearer ")){
            token= authHeader.substring(7);
            email= jwtService.extractEmail(token);
        }

        if(email!= null && SecurityContextHolder.getContext().getAuthentication()== null){
            UserDetails userDetails= myUserDetailsService.loadUserByUsername(email);

            if(jwtService.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                String role= jwtService.extractRole(token);

                System.out.println("The role in the jwtFilter is "+ role);

                authToken.setDetails(role);

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
