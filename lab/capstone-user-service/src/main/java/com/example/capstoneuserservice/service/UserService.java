package com.example.capstoneuserservice.service;

import com.example.capstoneuserservice.dto.UserDto;
import com.example.capstoneuserservice.jpa.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);

    Iterable<UserEntity> getUserByAll();

    UserDto getUserDetailsByEmail(String userName);

    void deleteUserByUserId(String userId);

    void deleteCellByUserIdAndCellId(String userId, String cellId);
}
