package com.example.capstonecellservice.service;

import com.example.capstonecellservice.dto.CellDto;
import com.example.capstonecellservice.jpa.CellEntity;
import com.example.capstonecellservice.jpa.CellRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CellServiceImpl implements CellService{
    CellRepository cellRepository;

    @Autowired
    public CellServiceImpl(CellRepository cellRepository) {
        this.cellRepository = cellRepository;
    }

    // cellId에 해당하는 정보만 퍼센테이지 계산해서 리턴
    @Override
    public CellDto getOneCellByUserId(String cellId) {
        CellEntity cellEntity = cellRepository.findByCellId(cellId);
        CellDto cellDto = new ModelMapper().map(cellEntity, CellDto.class);
        Long sum = Long.valueOf(cellDto.getLiveCellCount() + cellDto.getDieCellCount());
        cellDto.setLiveCellPercentage(sum / Long.valueOf(cellDto.getLiveCellCount()));
        cellDto.setDieCellPercentage(sum / Long.valueOf(cellDto.getDieCellCount()));

        return cellDto;
    }

    // userId 에 해당하는 전체 cell 정보를 퍼센테이지 계산해서 설정해서 List 에 넣어 리턴
    @Override
    public List<CellDto> getCellByUserId(String userId) {
        List<CellEntity> cellList = cellRepository.findCellsByUserId(userId);
        List<CellDto> result = new ArrayList<>();

        for(CellEntity cell : cellList){
            CellDto cellDto = new ModelMapper().map(cell, CellDto.class);
            Long sum = Long.valueOf(cellDto.getLiveCellCount() + cellDto.getDieCellCount());
            cellDto.setLiveCellPercentage(sum / Long.valueOf(cellDto.getLiveCellCount()));
            cellDto.setDieCellPercentage(sum / Long.valueOf(cellDto.getDieCellCount()));

            result.add(cellDto);
        }

        return result;
    }
}
