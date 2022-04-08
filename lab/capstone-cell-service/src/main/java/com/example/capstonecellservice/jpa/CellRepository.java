package com.example.capstonecellservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CellRepository extends JpaRepository<CellEntity, Long> {

    CellEntity findByUserId(String userId); //?
}
