package com.example.capstonecellservice.jpa;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CellRepository extends CrudRepository<CellEntity, Long> {
    Iterable<CellEntity> findCellsByUserId(String userId);

    CellEntity findByCellId(String cellId);

    void deleteByUserId(String userId);

    @Modifying(clearAutomatically = true)
    @Query("delete from CellEntity c where c.userId = :userId and c.cellId = :cellId")
    void deleteOneCell(String userId, String cellId);
}
