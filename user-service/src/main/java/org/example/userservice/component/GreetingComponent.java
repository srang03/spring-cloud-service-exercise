package org.example.userservice.component;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class GreetingComponent {
    @Value("${greeting.message}")
    private String message;
}
