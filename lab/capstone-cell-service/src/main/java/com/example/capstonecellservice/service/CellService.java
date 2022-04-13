package com.example.capstonecellservice.service;

import com.example.capstonecellservice.dto.CellDto;
import com.example.capstonecellservice.jpa.CellEntity;

public interface CellService {

    CellDto createCell(CellDto cellDto);

    CellDto getOneCellByCellId(String cellId); // cellId 통해 cell 정보 get

    Iterable<CellEntity> getCellsByUserId(String userId); // userId 통해 cell 정보들 get

    void deleteCellByUserId(String userId); // userId 통해 cell 전체 삭제

    void deleteCellByUserIdAndCellId(String userId, String cellId); // userId와 cellId 통해 해당하는 cell 삭제
}
