package com.example.capstonecellservice.jpa;

import com.example.capstonecellservice.dto.CellDto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CellRepository extends CrudRepository<CellRepository, Long> {
    List<CellEntity> findCellsByUserId(String userId);

    CellEntity findByCellId(String cellId);
}
