package com.example.capstonecellservice.service;

import com.example.capstonecellservice.dto.CellDto;
import com.example.capstonecellservice.jpa.CellEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CellService {
    CellDto createCell(CellDto cellDto);

    CellDto getOneCellByUserId(String cellId);

    Iterable<CellEntity> getCellByUserId(String userId);
}
