package com.example.capstonecellservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CellRepository extends JpaRepository<CellEntity, Long> {

    Iterable<CellEntity> findCellsByUserId(String userId);

    CellEntity findByCellId(String cellId);

    void deleteByUserId(String userId);

    @Modifying(clearAutomatically = true)
    @Query("delete from CellEntity c where c.userId = :userId and c.cellId = :cellId")
    void deleteByUserIdAndCellId(String userId, String cellId);
}
