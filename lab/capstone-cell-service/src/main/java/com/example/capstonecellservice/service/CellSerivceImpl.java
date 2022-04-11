package com.example.capstonecellservice.service;

import com.example.capstonecellservice.dto.CellDto;
import com.example.capstonecellservice.jpa.CellEntity;
import com.example.capstonecellservice.jpa.CellRepository;
import com.example.capstonecellservice.service.exception.CellnameNotFoundException;
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
    public CellDto getOneCellByCellId(String cellId) {
        CellEntity cellEntity = cellRepository.findByCellId(cellId);

//        if (cellEntity == null) {
//            throw new CellnameNotFoundException("Cell not found");
//        }
        CellDto cellDto = new ModelMapper().map(cellEntity, CellDto.class);

        return cellDto;
    }

    @Override
    public Iterable<CellEntity> getCellsByUserId(String userId) {
        return cellRepository.findCellsByUserId(userId);
    }

    @Override
    public void deleteCellByUserId(String userId, String cellId) {
        cellRepository.deleteByUserId(userId, cellId);
    }
}
