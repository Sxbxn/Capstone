package com.example.capstonecellservice.controller;

import com.example.capstonecellservice.dto.CellDto;
import com.example.capstonecellservice.jpa.CellEntity;
import com.example.capstonecellservice.service.AwsS3Service;
import com.example.capstonecellservice.service.CellService;
import com.example.capstonecellservice.vo.RequestCell;
import com.example.capstonecellservice.vo.ResponseCell;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class CellController {

    CellService cellService;
    private AwsS3Service awsS3Service;

    @Autowired
    public CellController(CellService cellService, AwsS3Service awsS3Service) {
        this.cellService = cellService;
        this.awsS3Service = awsS3Service;
    }

    //Create
    //cell 새로 생성
    @PostMapping("/{userId}/cells")
    public ResponseEntity<ResponseCell> createCell(@PathVariable("userId") String userId, @RequestBody RequestCell cell) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        CellDto cellDto = mapper.map(cell, CellDto.class);
        cellDto.setUserId(userId);

        cellDto.setViability((cellDto.getLiveCell() / (double) cellDto.getTotalCell()) * 100);
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
    //user의 전체 cell 삭제 (회원 탈퇴시)
    @DeleteMapping("/{userId}")
    public void deleteCells(@PathVariable("userId") String userId) {
        cellService.deleteCellByUserId(userId);
    }

    //user의 특정 cell 삭제
    @DeleteMapping("/{userId}/{cellId}")
    public void deleteCell(@PathVariable("userId") String userId, @PathVariable("cellId") String cellId){
        cellService.deleteCellByUserIdAndCellId(userId, cellId);
    }

    /**
     * Amazon S3에 이미지 업로드
     */
    @PostMapping("/images")
    public String uploadImage(@RequestPart(value = "file", required = false) MultipartFile multipartFile) {
        return awsS3Service.uploadImage(multipartFile);
    }

    /**
     * Amazon S3에 이미지 업로드 된 파일을 삭제
     */
    @DeleteMapping("/images")
    public void deleteImage(@RequestParam String fileName) {
        awsS3Service.deleteImage(fileName);
    }
}
