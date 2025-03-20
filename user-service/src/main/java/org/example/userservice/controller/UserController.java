package org.example.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.example.userservice.component.GreetingComponent;
import org.example.userservice.data.dto.UserDto;
import org.example.userservice.data.entity.UserEntity;
import org.example.userservice.data.vo.RequestUser;
import org.example.userservice.data.vo.ResponseUser;
import org.example.userservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/users")
    public ResponseEntity<ResponseUser> joinUser(@RequestBody RequestUser user){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userService.createUser(userDto);

        ResponseUser responseUser = modelMapper.map(userDto, ResponseUser.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    // 로그인
    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers(){
        Iterable<UserDto> userDtos = userService.retrieveAllUsers();
        List<ResponseUser> responseUsers = new ArrayList<>();
        userDtos.forEach(userDto -> {
            responseUsers.add(new ModelMapper().map(userDto, ResponseUser.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(responseUsers);
    }
    
    // 유저 정보 조회
    @GetMapping("/users/{userid}")
    public ResponseEntity<ResponseUser> getUserById(@PathVariable("userid") String userid){
        UserDto userDto = userService.retrieveUserById(userid);
        return ResponseEntity.status(HttpStatus.OK).body(new ModelMapper().map(userDto, ResponseUser.class));
    }
}
