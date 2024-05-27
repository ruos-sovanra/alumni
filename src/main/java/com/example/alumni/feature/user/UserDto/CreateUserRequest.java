package com.example.alumni.feature.user.UserDto;

import com.example.alumni.feature.password.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;

@Builder
@Validated
@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
public record   CreateUserRequest(
        String firstName,
        String lastName,
        String username,
        @Email(message = "Email format is not correct!")
        String email,
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must be at least 8 characters and include at least one uppercase letter, one lowercase letter, one number, and one special character")
        String password,
        String confirmPassword,
        String phone,
        String avatar,
        String roleName,
        String AccTypeName

) {
}
