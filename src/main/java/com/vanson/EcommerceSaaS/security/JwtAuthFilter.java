package com.vanson.EcommerceSaaS.security;

import com.vanson.EcommerceSaaS.entity.User;
import com.vanson.EcommerceSaaS.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter  extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain)
        throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        //Không có token -> bỏ qua
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }

        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        // lấy user từ DB
        if (email != null) {
            User user = userRepository.findByEmail(email).orElse(null);
            if (user != null) {
                SecurityContextHolder.getContext().setAuthentication(
                        new JwtAuthenticationToken(user.getEmail(), user.getRole())
                );
            }
        }

        filterChain.doFilter(request,response);
    }
}