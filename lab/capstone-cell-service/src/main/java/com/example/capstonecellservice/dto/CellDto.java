package com.example.capstonecellservice.dto;

import lombok.Data;

@Data
public class CellDto {

    private String cellId;

    private Integer totalCell;
    private Integer liveCell;
    private Integer deadCell;
    private Double viability;

    private String userId;
}
