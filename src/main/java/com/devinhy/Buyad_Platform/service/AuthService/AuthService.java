package com.devinhy.Buyad_Platform.service.AuthService;

import com.devinhy.Buyad_Platform.auth.dto.LoginRequest;
import com.devinhy.Buyad_Platform.auth.dto.LoginResponse;
import com.devinhy.Buyad_Platform.auth.dto.RegisterRequest;
import com.devinhy.Buyad_Platform.auth.dto.UserResponse;
import com.devinhy.Buyad_Platform.exception.AdminAlreadyExistsException;
import com.devinhy.Buyad_Platform.exception.EmailAlreadyExistsException;
import com.devinhy.Buyad_Platform.exception.InvalidCredentialsException;
import com.devinhy.Buyad_Platform.model.EmailVerificationToken;
import com.devinhy.Buyad_Platform.model.Role;
import com.devinhy.Buyad_Platform.model.User;
import com.devinhy.Buyad_Platform.repository.EmailVerificationTokenRepository;
import com.devinhy.Buyad_Platform.repository.UserRepository;
import com.devinhy.Buyad_Platform.service.EmailService;
import com.devinhy.Buyad_Platform.service.JwtService;
import com.devinhy.Buyad_Platform.util.TokenGenerator;
import jakarta.mail.MessagingException;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.hibernate.jdbc.Expectation;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final EmailVerificationTokenRepository tokenRepository;
    public UserResponse register(RegisterRequest request) throws MessagingException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        if (request.getRole() == Role.ADMIN && userRepository.existsByRole(Role.ADMIN)) {
            throw new AdminAlreadyExistsException("An Admin already exists");
        }
        User user = User.builder()
                                .username(request.getUsername())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .role(request.getRole())
                                .build();
        user.setEmailVerified(false);
        User saveUser =  userRepository.save(user);
        String token = TokenGenerator.generateToken();
        EmailVerificationToken verificationToken = new EmailVerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(saveUser);
        verificationToken.setCreatedAt(LocalDateTime.now());
        verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24));
        tokenRepository.save(verificationToken);
        emailService.sendVerificationEmail(saveUser.getEmail(), token);
        return new UserResponse(saveUser.getId(), saveUser.getUsername(), saveUser.getEmail(), saveUser.getRole());
    }


    public LoginResponse login(LoginRequest request) throws InvalidCredentialsException {
        String email = request.getEmail().trim().toLowerCase();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new InvalidCredentialsException("Invalid email or password."));

        if (!user.isEmailVerified()) {
            throw new InvalidCredentialsException("Please verify your email before logging in.");
        }
        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!passwordMatches) {
            throw new InvalidCredentialsException("Invalid email or password.");
        }

        String token = jwtService.generateToken(user);

        UserResponse userResponse = new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole());

        return new LoginResponse(token, userResponse);
    }
}
