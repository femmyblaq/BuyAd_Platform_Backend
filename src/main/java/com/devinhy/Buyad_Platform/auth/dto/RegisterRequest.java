package com.devinhy.Buyad_Platform.auth.dto;
import com.devinhy.Buyad_Platform.model.Role;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class RegisterRequest {
    @NotBlank(message = "Name is required")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email")
    private String email;

    @NotBlank(message = "Password is required")
    @Length(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotNull(message = "Role must be provided")
    private Role role;
}
