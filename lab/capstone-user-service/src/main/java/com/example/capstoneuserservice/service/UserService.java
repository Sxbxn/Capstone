package com.example.capstoneuserservice.service;

import com.example.capstoneuserservice.dto.UserDto;
import com.example.capstoneuserservice.jpa.UserEntity;
import com.example.capstoneuserservice.vo.RequestUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);

    Iterable<UserEntity> getUserByAll();

    UserDto getUserDetailsByEmail(String userName);

    void deleteUserByUserId(String userId);

    void deleteCellByUserIdAndCellId(String userId, String cellId);

    @Transactional
    UserDto updateUser(UserDto userDto);
}
