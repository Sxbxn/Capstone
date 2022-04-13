package com.example.capstonecellservice.controller;

import com.example.capstonecellservice.dto.CellDto;
import com.example.capstonecellservice.jpa.CellEntity;
import com.example.capstonecellservice.service.CellService;
import com.example.capstonecellservice.vo.RequestCell;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class CellController {
    CellService cellService;

    @Autowired
    public CellController(CellService cellService) {
        this.cellService = cellService;
    }

    // cell 생성함
    @PostMapping("/{userId}/cells")
    public CellDto createCell(@PathVariable("userId") String userId,
                              @RequestBody RequestCell cell) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        CellDto cellDto = mapper.map(cell, CellDto.class);
        cellDto.setUserId(userId);
        Double sum = Double.valueOf(cellDto.getLiveCellCount() + cellDto.getDieCellCount());
        cellDto.setLiveCellPercentage((Double.valueOf(cellDto.getLiveCellCount()) / sum)*100);

        CellDto createCell = cellService.createCell(cellDto);

        return createCell;
    }

    // 전체 cell 을 불러옴
    @GetMapping("/{userId}/cells")
    public List<CellDto> getCells(@PathVariable("userId") String userId) {
        Iterable<CellEntity> cells = cellService.getCellByUserId(userId);
        List<CellDto> result = new ArrayList<>();
        cells.forEach(v ->{
            result.add(new ModelMapper().map(v, CellDto.class));
        });

        return result;
    }

    // cellId 에 해당하는 하나만 불러옴
    @GetMapping("/{cellId}")
    public CellDto getCell(@PathVariable("cellId") String cellId) {
        CellDto cellDto = cellService.getOneCellByUserId(cellId);

        return cellDto;
    }

    // 회원 탈퇴시 해당하는 userId 를 가지고있는 모든 cell 삭제
    @DeleteMapping("/{userId}")
    public void deleteCells(@PathVariable("userId") String userId) {
        cellService.deleteCellByUserId(userId);
    }

    // 회원이 가지고있는 특정한 cellId 하나 삭제
    @DeleteMapping(value = {"/{userId}/{cellId}"})
    public void deleteCell(@PathVariable("userId") String userId,
                           @PathVariable("cellId") String cellId) {
        cellService.deleteCellByUserIdAndCellId(userId, cellId);
    }
}
