package com.devinhy.Buyad_Platform.service.AuthService;

import com.devinhy.Buyad_Platform.auth.dto.LoginRequest;
import com.devinhy.Buyad_Platform.auth.dto.LoginResponse;
import com.devinhy.Buyad_Platform.auth.dto.RegisterRequest;
import com.devinhy.Buyad_Platform.auth.dto.UserResponse;
import com.devinhy.Buyad_Platform.exception.AdminAlreadyExistsException;
import com.devinhy.Buyad_Platform.exception.EmailAlreadyExistsException;
import com.devinhy.Buyad_Platform.exception.InvalidCredentialsException;
import com.devinhy.Buyad_Platform.model.Role;
import com.devinhy.Buyad_Platform.model.User;
import com.devinhy.Buyad_Platform.repository.UserRepository;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(RegisterRequest request) {
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

        User saveUser =  userRepository.save(user);
        return new UserResponse(saveUser.getId(), saveUser.getUsername(), saveUser.getEmail(), saveUser.getRole());
    }

    public LoginResponse login(LoginRequest request) throws InvalidCredentialsException {
        String email = request.getEmail().trim().toLowerCase();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new InvalidCredentialsException("Invalid email or password."));

        if (!user.isEmailVerified()) {
            throw new InvalidCredentialsException("Invalid email or password.");
        }


    }
}
