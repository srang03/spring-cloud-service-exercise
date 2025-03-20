package org.example.userservice.service;

import org.example.userservice.data.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);
    UserDto retrieveUserById(String userid);
    Iterable<UserDto> retrieveAllUsers();
    UserDto retrieveUserDetailsByEmail(String email);
    UserDto retrieveUserByName(String name);
}
