package com.example.capstonecellservice.service;

import com.example.capstonecellservice.dto.CellDto;
import com.example.capstonecellservice.jpa.CellEntity;
import com.example.capstonecellservice.jpa.CellRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CellServiceImpl implements CellService{
    CellRepository cellRepository;

    @Autowired
    public CellServiceImpl(CellRepository cellRepository) {
        this.cellRepository = cellRepository;
    }

    @Override
    public CellDto createCell(CellDto cellDto) {
        cellDto.setCellId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CellEntity cellEntity = mapper.map(cellDto, CellEntity.class);

        cellRepository.save(cellEntity);

        CellDto returnValue = mapper.map(cellEntity, CellDto.class);

        return returnValue;
    }

    // cellId에 해당하는 정보만 퍼센테이지 계산해서 리턴
    @Override
    public CellDto getOneCellByUserId(String cellId) {
        CellEntity cellEntity = cellRepository.findByCellId(cellId);
        CellDto cellDto = new ModelMapper().map(cellEntity, CellDto.class);

        return cellDto;
    }

    // userId 에 해당하는 전체 cell 정보를 퍼센테이지 계산해서 설정해서 List 에 넣어 리턴
    @Override
    public Iterable<CellEntity> getCellByUserId(String userId) {
        return cellRepository.findCellsByUserId(userId);
    }

    @Override
    public void deleteCellByUserId(String userId) {
        cellRepository.deleteByUserId(userId);
    }

    @Override
    public void deleteCellByUserIdAndCellId(String userId, String cellID) {
        cellRepository.deleteOneCell(userId, cellID);
    }
}
