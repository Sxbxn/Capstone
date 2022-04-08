package com.example.capstonecellservice.service;

import com.example.capstonecellservice.dto.CellDto;
import com.example.capstonecellservice.jpa.CellEntity;
import com.example.capstonecellservice.jpa.CellRepository;

public class CellSerivceImpl implements CellService {

    CellRepository cellRepository;

    @Override
    public CellDto getCellByCellId(String cellId) {
        return null;
    }

    @Override
    public CellDto getCellByUserId(String userId) {
        return null;
    }

    @Override
    public Iterable<CellEntity> getCellByAll() {
        return cellRepository.findAll();
    }
}
