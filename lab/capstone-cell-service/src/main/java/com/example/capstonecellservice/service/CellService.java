package com.example.capstonecellservice.service;

import com.example.capstonecellservice.dto.CellDto;
import com.example.capstonecellservice.jpa.CellEntity;

public interface CellService {

    CellDto createCell(CellDto cellDto);


    CellDto getCellByCellId(String cellId); // cellId 통해 cell 정보 get

    CellDto getCellByUserId(String userId); // userId 통해 cell 정보 get

    Iterable<CellEntity> getCellByAll();


}
