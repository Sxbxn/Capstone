package com.example.capstonecellservice.service;

import com.example.capstonecellservice.dto.CellDto;
import com.example.capstonecellservice.jpa.CellEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CellService {
    CellDto getOneCellByUserId(String cellId);

    List<CellDto> getCellByUserId(String userId);
}
