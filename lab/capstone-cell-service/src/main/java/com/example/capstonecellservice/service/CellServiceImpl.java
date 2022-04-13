package com.example.capstonecellservice.service;

import com.example.capstonecellservice.dto.CellDto;
import com.example.capstonecellservice.jpa.CellEntity;
import com.example.capstonecellservice.jpa.CellRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class CellServiceImpl implements CellService {

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

        CellDto returnCellDto = mapper.map(cellEntity, CellDto.class);

        return returnCellDto;
    }

    @Override
    public CellDto getOneCellByCellId(String cellId) {
        CellEntity cellEntity = cellRepository.findByCellId(cellId);

        CellDto cellDto = new ModelMapper().map(cellEntity, CellDto.class);

        return cellDto;
    }

    @Override
    public Iterable<CellEntity> getCellsByUserId(String userId) {
        return cellRepository.findCellsByUserId(userId);
    }

    @Override
    public void deleteCellByUserId(String userId) {
        cellRepository.deleteByUserId(userId);
    }

    @Override
    public void deleteCellByUserIdAndCellId(String userId, String cellId) {
        cellRepository.deleteByUserIdAndCellId(userId, cellId);
    }
}
