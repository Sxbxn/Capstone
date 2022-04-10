package com.example.capstonecellservice.dto;

import lombok.Data;

@Data
public class CellDto {
    private String cellId;
    private Integer liveCellCount;
    private Integer dieCellCount;
    private Double liveCellPercentage;

    private String userId;
}
