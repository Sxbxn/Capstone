package com.example.capstonecellservice.controller;

import com.example.capstonecellservice.dto.CellDto;
import com.example.capstonecellservice.dto.UrlDto;
import com.example.capstonecellservice.jpa.CellEntity;
import com.example.capstonecellservice.service.AwsS3Service;
import com.example.capstonecellservice.service.CellService;
import com.example.capstonecellservice.vo.FlaskResponseDto;
import com.example.capstonecellservice.vo.RequestCell;
import com.example.capstonecellservice.vo.ResponseCell;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
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
    public void deleteCell(@PathVariable("userId") String userId, @PathVariable("cellId") String cellId) {
        cellService.deleteCellByUserIdAndCellId(userId, cellId);
    }

    /**
     * Amazon S3에 이미지 업로드
     */
    // @PostMapping("/{userId}/cells")
    @PostMapping("/images")
    public UrlDto uploadImage(@RequestPart(value = "file", required = false) MultipartFile multipartFile) {
        UrlDto urlDto = new UrlDto();
        FlaskResponseDto flaskResponseDto = StartFlask(multipartFile.getOriginalFilename(), multipartFile);

        /**
         *
         * 로컬에서 flask 를 통해 뭐 실행을 해
         *
         * cellDto.setCellId(UUID.randomUUID().toString());
         * json 파일을 이제 cellDto에 저장,
         * cellDto.set(totalcell), cellDto.set(livecell), cellDto.set(deadcell);
         * 퍄일을 받거나, 파일을 어떻게 뭐 저장해서, 경로를 어떻게하진 모르겠지만 알아내서
         * cellDto.setUrl(awsS3Service.uploadImage(multipartFIle));
         *
         */

        urlDto.setUrl(awsS3Service.uploadImage(multipartFile));


        return urlDto;
        //return cellDto;
    }

    /**
     * Amazon S3에 이미지 업로드 된 파일을 삭제
     */
    @DeleteMapping("/images")
    public void deleteImage(@RequestParam String fileName) {
        awsS3Service.deleteImage(fileName);
    }

    /**
     * flask 서버 start
     */
    private FlaskResponseDto StartFlask(String fileName, MultipartFile multipartFile) {
        RestTemplate restTemplate = new RestTemplate();
        HttpStatus httpStatus = HttpStatus.CREATED;
        HttpEntity<String> response = null;
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        FlaskResponseDto dto = null;

        try {
            if (!multipartFile.isEmpty()) {
                body.add("filename", fileName);
                body.add("file",
                        new MultipartInputStreamFileResource(multipartFile.getInputStream(), multipartFile.getOriginalFilename()));
            }
            // Header Set
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            String url = "http://localhost:50000/AI";

            HttpEntity<?> requestMessage = new HttpEntity<>(body, headers);
            response = restTemplate.postForEntity(url, requestMessage, String.class);

            // Response Body 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
            dto = objectMapper.readValue(response.getBody(), FlaskResponseDto.class);
        } catch (HttpStatusCodeException e) {
            httpStatus = HttpStatus.valueOf(e.getStatusCode().value());
        } catch (Exception e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

//        FlaskResponseDto flaskResponseDto = new FlaskResponseDto();

        return dto;
    }

    class MultipartInputStreamFileResource extends InputStreamResource {

        private final String filename;

        MultipartInputStreamFileResource(InputStream inputStream, String filename) {
            super(inputStream);
            this.filename = filename;
        }

        @Override
        public String getFilename() {
            return this.filename;
        }

        @Override
        public long contentLength() throws IOException {
            return -1; // we do not want to generally read the whole stream into memory ...
        }
    }
}
