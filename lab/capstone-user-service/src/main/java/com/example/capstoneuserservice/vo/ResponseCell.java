package com.example.capstoneuserservice.vo;

import lombok.Data;

@Data
public class ResponseCell {
    private String cellId;
    private Integer liveCellCount;
    private Integer dieCellCount;
    private Double liveCellPercentage;
}
