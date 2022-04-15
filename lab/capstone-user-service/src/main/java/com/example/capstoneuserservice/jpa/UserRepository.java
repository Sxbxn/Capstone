package com.example.capstoneuserservice.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByUserId(String userId);

    UserEntity findByEmail(String username);

    void deleteByUserId(String userId);

}

