package com.example.capstonecellservice.service;

import com.example.capstonecellservice.dto.CellDto;
import com.example.capstonecellservice.jpa.CellEntity;
import com.example.capstonecellservice.jpa.CellRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
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
    public CellDto createCell(CellDto cellDto) throws IOException, ParseException {

        cellDto.setCellId(UUID.randomUUID().toString());

        File dir = new File("/Users/subin/Desktop/predict/");

        FilenameFilter filter= new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".json");
            }
        };

//        File[] files = directory.listFiles(File::isFile);

        File[] files = dir.listFiles(filter);

        long lastModifiedTime = Long.MIN_VALUE;
        File chosenFile = null;

        if (files != null) {
            for (File file: files) {
                if (file.lastModified() > lastModifiedTime) {
                    chosenFile = file;
                    lastModifiedTime = file.lastModified();
                }
            }
        }

        Reader reader = new FileReader(chosenFile);

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(reader);

        cellDto.setTotalCell(Integer.valueOf(jsonObject.get("totalCell").toString()));
        cellDto.setLiveCell(Integer.valueOf(jsonObject.get("liveCell").toString()));
        cellDto.setDeadCell(Integer.valueOf(jsonObject.get("deadCell").toString()));

        cellDto.setViability((cellDto.getLiveCell() / (double) cellDto.getTotalCell()) * 100);

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
