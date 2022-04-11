package com.example.capstonecellservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CellRepository extends JpaRepository<CellEntity, Long> {

    Iterable<CellEntity> findCellsByUserId(String userId);

    CellEntity findByCellId(String cellId);

    @Transactional
    void deleteByUserId(String userId, String cellId);
}
