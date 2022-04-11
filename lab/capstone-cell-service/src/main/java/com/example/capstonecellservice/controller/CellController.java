package com.example.capstonecellservice.controller;

import com.example.capstonecellservice.dto.CellDto;
import com.example.capstonecellservice.jpa.CellEntity;
import com.example.capstonecellservice.service.CellService;
import com.example.capstonecellservice.vo.RequestCell;
import com.example.capstonecellservice.vo.ResponseCell;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/capstone-cell-service")
public class CellController {

    private CellService cellService;

    //Create
    //cell 새로 생성
    @PostMapping("/{userId}/cells")
    public ResponseEntity<ResponseCell> createCell(@PathVariable("userId") String userId, @RequestBody RequestCell cell) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        CellDto cellDto = mapper.map(cell, CellDto.class);
        cellDto.setUserId(userId);
        cellDto.setViability((double) (cellDto.getLiveCell() / cellDto.getTotalCell()) * 100);
        cellService.createCell(cellDto);

        ResponseCell responseCell = mapper.map(cellDto, ResponseCell.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseCell);
    }

    //Read
    //user의 cell 리스트 조회
    @GetMapping("/{userId}/cells")
    public ResponseEntity<List<ResponseCell>> getCells(@PathVariable("userId") String userId) {
        Iterable<CellEntity> findcells = cellService.getCellsByUserId(userId);

        List<ResponseCell> result = new ArrayList<>();
        findcells.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseCell.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //CellId를 통한 특정 cell 조회
    @GetMapping("/cells/{cellId}")
    public ResponseEntity<ResponseCell> getOneCell(@PathVariable("cellId") String cellId) {
        CellDto cellDto = cellService.getOneCellByCellId(cellId);

        ResponseCell returnValue = new ModelMapper().map(cellDto, ResponseCell.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    //Delete
    //user의 특정 cell 삭제
    @DeleteMapping("/{userId}/{cellId}")
    public void deleteCell(@PathVariable("userId") String userId, @PathVariable("cellId") String cellId){
        cellService.deleteCellByUserId(userId, cellId);
    }


}
