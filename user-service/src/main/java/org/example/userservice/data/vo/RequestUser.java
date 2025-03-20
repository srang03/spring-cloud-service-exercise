package org.example.userservice.data.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestUser {
    @NotNull(message = "Email cannot be null")
    @Size(min=2, message ="Email not be less than two characters")
    @Email
    private String email;

    @NotNull(message = "Name not be less than two characters")
    @Size(min= 2)
    private String name;

    @NotNull(message = "Password  not be less than six characters")
    @Size(min= 4)
    private String pwd;

}
