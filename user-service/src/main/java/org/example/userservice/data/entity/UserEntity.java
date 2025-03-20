package org.example.userservice.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 100, unique = true)
    private String pwd;
    @Column(nullable = false, length = 50, unique = true)
    private String userId;

    private Date createAt;

    private String encryptedPwd;
}
