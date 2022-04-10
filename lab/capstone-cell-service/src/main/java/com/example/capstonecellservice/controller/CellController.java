package com.example.capstonecellservice.controller;

import com.example.capstonecellservice.dto.CellDto;
import com.example.capstonecellservice.service.CellService;
import com.example.capstonecellservice.vo.RequestCell;
import com.example.capstonecellservice.vo.ResponseCell;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/capstone-cell-service")
public class CellController {

    private CellService cellService;

    @PostMapping("/{userId}/cells")
    public ResponseEntity<ResponseCell> createCell(@PathVariable("userId") String userId, @RequestBody RequestCell cell) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        CellDto cellDto = mapper.map(cell, CellDto.class);
        cellDto.setUserId(userId);
        cellDto.setViability(cellDto.getTotalCell() / cellDto.getTotalCell());
        cellService.createCell(cellDto);

        ResponseCell responseCell = mapper.map(cellDto, ResponseCell.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseCell);
    }

}
