package org.example.userservice.service;

import feign.FeignException;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.example.userservice.feign.client.OrderServiceClient;
import org.example.userservice.data.dto.UserDto;
import org.example.userservice.data.entity.UserEntity;
import org.example.userservice.data.vo.ResponseOrder;
import org.example.userservice.feign.error.FeignErrorDecoder;
import org.example.userservice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    Environment env;
    RestTemplate restTemplate;

    OrderServiceClient orderServiceClient;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder
            , Environment env, RestTemplate restTemplate, OrderServiceClient orderServiceClient
    ) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.env = env;
        this.restTemplate = restTemplate;
        this.orderServiceClient = orderServiceClient;
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
    public UserDto retrieveUserById(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

//        String orderUrl = String.format(env.getProperty("order_service.url"), userid) ;
//        ResponseEntity<List<ResponseOrder>> orderListResponse = restTemplate.exchange(orderUrl,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<ResponseOrder>>() {
//                });
//
//        List<ResponseOrder> orders = orderListResponse.getBody();

//        List<ResponseOrder> orders = null;
//        try {
//            orders = orderServiceClient.getOrders(userId);
//        } catch (FeignException ex) {
//            log.error(ex.getMessage());
//        }

        List<ResponseOrder> orders = orderServiceClient.getOrders(userId);
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

        String orderUrl = String.format(env.getProperty("order_service.url"), userDto.getUserId());
        ResponseEntity<List<ResponseOrder>> orderListResponse = restTemplate.exchange(orderUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ResponseOrder>>() {
                });

        List<ResponseOrder> orders = orderListResponse.getBody();
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
