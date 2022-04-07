package com.example.capstoneuserservice.service;

import com.example.capstoneuserservice.dto.UserDto;
import com.example.capstoneuserservice.jpa.UserEntity;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);

    Iterable<UserEntity> getUserByAll();
}
