package com.devinhy.Buyad_Platform.security;

import com.devinhy.Buyad_Platform.model.User;
import com.devinhy.Buyad_Platform.repository.UserRepository;
import com.devinhy.Buyad_Platform.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String authHeader = req.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }
        String token = authHeader.substring(7);
        try {
            if(!jwtService.isTokenValid(token)){
                chain.doFilter(req, res);
                return;
            }
            String email =  jwtService.extractMail(token);
            User user =userRepository.findByEmail(email).orElse(null);

            if(user == null){
                chain.doFilter(req, res);
                return;
            }
            String role =jwtService.extractRole(token);

            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+role);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, List.of(authority));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        chain.doFilter(req, res);
    }
}
