package com.example.capstoneuserservice.controller;

import com.example.capstoneuserservice.dto.UserDto;
import com.example.capstoneuserservice.jpa.UserEntity;
import com.example.capstoneuserservice.service.UserService;
import com.example.capstoneuserservice.vo.RequestUser;
import com.example.capstoneuserservice.vo.ResponseUser;
import org.bouncycastle.math.raw.Mod;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {

    private Environment env;
    private UserService userService;
    private CircuitBreakerFactory circuitBreakerFactory;

    @Autowired
    public UserController(Environment env, UserService userService, CircuitBreakerFactory circuitBreakerFactory) {
        this.env = env;
        this.userService = userService;
    }

    // 현재 user-service 상태 확인
    @GetMapping("/health_check")
    public String status() {
//        return String.format("It's Working in User Service");
//                + ", port(local.server.port)=" + env.getProperty("local.server.port")
//                + ", port(server.port)=" + env.getProperty("server.port")
////                + ", gateway ip=" + env.getProperty("gateway.ip")
////                + ", message=" + env.getProperty("greeting.message")
//                + ", token secret=" + env.getProperty("token.secret")
//                + ", token expiration time=" + env.getProperty("token.expiration_time"));
        return String.format("It's Working in User Service on Port %s",
                env.getProperty("local.server.port"));
    }

    // user-service 가 잘 실행되는지 확인
    @GetMapping
    public String welcome(){
        return env.getProperty("greeting.message");
    }

    // user 새로 생성
    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(user, UserDto.class);
        userService.createUser(userDto);

        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    // 모든 user 조회
    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers(){
        Iterable<UserEntity> userList = userService.getUserByAll();

        List<ResponseUser> result = new ArrayList<>();
        userList.forEach(v ->{
            result.add(new ModelMapper().map(v, ResponseUser.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 특정 user 조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId){
        UserDto userDto = userService.getUserByUserId(userId);

        ResponseUser returnValue = new ModelMapper().map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    // 특정 user 삭제
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") String userId){
        userService.deleteUserByUserId(userId);
    }
    // 특정 user 변경
}
