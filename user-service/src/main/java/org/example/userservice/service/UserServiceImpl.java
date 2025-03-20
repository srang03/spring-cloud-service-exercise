package org.example.userservice.service;

import jakarta.ws.rs.NotFoundException;
import org.example.userservice.data.dto.UserDto;
import org.example.userservice.data.entity.UserEntity;
import org.example.userservice.data.vo.ResponseOrder;
import org.example.userservice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        userDto.setCreateAt(new Date());
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(bCryptPasswordEncoder.encode(userDto.getPwd()));
        userRepository.save(userEntity);
        return userDto;
    }

    @Override
    public UserDto retrieveUserById(String userid) {
        UserEntity userEntity = userRepository.findByUserId(userid);


        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
        List<ResponseOrder> orders = new ArrayList<>();
        userDto.setOrders(orders);

        return userDto;
    }

    @Override
    public Iterable<UserDto> retrieveAllUsers() {
        Iterable<UserEntity> users = userRepository.findAll();

        List<UserDto> userDtos = new ArrayList<>();
        for (UserEntity userEntity : users) {
            UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public UserDto retrieveUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);



        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
        List<ResponseOrder> orders = new ArrayList<>();
        userDto.setOrders(orders);

        return userDto;
    }

    @Override
    public UserDto retrieveUserByName(String name) throws NotFoundException {
        UserEntity userEntity = userRepository.findByEmail(name);

        if (userEntity == null) {
            throw new NotFoundException("user not found");
        }

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
        List<ResponseOrder> orders = new ArrayList<>();
        userDto.setOrders(orders);

        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);

        if (userEntity == null)
            throw new UsernameNotFoundException(username + ": not found");

        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(),
                true, true, true, true,
                new ArrayList<>());
    }
}
