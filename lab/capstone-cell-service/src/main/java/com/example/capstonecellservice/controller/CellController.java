package com.example.capstonecellservice.controller;

import com.example.capstonecellservice.dto.CellDto;
import com.example.capstonecellservice.service.CellService;
import com.example.capstonecellservice.vo.RequestCell;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class CellController {

    CellService cellService;

    @Autowired
    public CellController(CellService cellService) {
        this.cellService = cellService;
    }

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


}
