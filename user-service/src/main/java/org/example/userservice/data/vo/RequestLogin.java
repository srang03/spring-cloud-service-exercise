package org.example.userservice.data.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestLogin {
    @NotNull(message = "Email can not be null")
    @Size(min=3, message = "Email not be less than two characters")
    @Email
    private String email;

    @NotNull(message = "password can not be null")
    @Size(min=8, message = "password not be less than eight characters")
    private String password;
}
