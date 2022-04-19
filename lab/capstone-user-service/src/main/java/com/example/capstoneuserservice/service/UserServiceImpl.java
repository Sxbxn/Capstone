package com.example.capstoneuserservice.service;

import com.example.capstoneuserservice.client.CellServiceClient;
import com.example.capstoneuserservice.dto.UserDto;
import com.example.capstoneuserservice.jpa.UserEntity;
import com.example.capstoneuserservice.jpa.UserRepository;
import com.example.capstoneuserservice.vo.ResponseCell;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;
    CellServiceClient cellServiceClient;
    CircuitBreakerFactory circuitBreakerFactory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);
        if (userEntity == null)
            throw new UsernameNotFoundException(username);

        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(),
                true, true, true, true,
                new ArrayList<>());
    }

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           CircuitBreakerFactory circuitBreakerFactory,
                           CellServiceClient cellServiceClient) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.circuitBreakerFactory = circuitBreakerFactory;
        this.cellServiceClient = cellServiceClient;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));

        userRepository.save(userEntity);

        UserDto returnUserDto = mapper.map(userEntity, UserDto.class);

        return returnUserDto;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        CircuitBreaker circuitbreaker = circuitBreakerFactory.create("circuitbreaker");
        List<ResponseCell> cellsList = circuitbreaker.run(() -> cellServiceClient.getCells(userId),
                throwable -> new ArrayList<>());
        userDto.setCells(cellsList);

        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
        return userDto;
    }

    @Override
    public void deleteUserByUserId(String userId) {
        userRepository.deleteByUserId(userId);
        cellServiceClient.deleteCells(userId);
    }

    @Override
    public void deleteAllCellByUserId(String userId) {
        cellServiceClient.deleteCells(userId);
    }

    @Override
    public void deleteCellByUserIdAndCellId(String userId, String cellId) {
        cellServiceClient.deleteCell(userId, cellId);
    }

    @Override
    public UserDto updateUserInfo(UserDto userDto) {
        UserEntity userEntity = userRepository.findByEmail(userDto.getEmail());

        userEntity.setEmail(userDto.getEmail()); //  putmapping 할 때 이메일은 똑같은거 사용
        userEntity.setName(userDto.getName()); // RequestUser에서 변경하거나 바꾸려고 입력한 것 사용
        userEntity.setUserId(userEntity.getUserId()); // userId는 userentity의 userId 그대로 사용
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));

        userRepository.save(userEntity);

        return userDto;
    }
}