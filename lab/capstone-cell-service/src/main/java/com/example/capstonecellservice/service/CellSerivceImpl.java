package com.example.capstonecellservice.service;

import com.example.capstonecellservice.dto.CellDto;
import com.example.capstonecellservice.jpa.CellEntity;
import com.example.capstonecellservice.jpa.CellRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.UUID;

public class CellSerivceImpl implements CellService {

    CellRepository cellRepository;

    @Override
    public CellDto createCell(CellDto cellDto) {
        cellDto.setUserId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        CellEntity cellEntity = mapper.map(cellDto, CellEntity.class);
        cellRepository.save(cellEntity);

        CellDto returnCellDto = mapper.map(cellEntity, CellDto.class);

        return returnCellDto;
    }

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
