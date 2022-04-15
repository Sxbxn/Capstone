package com.example.capstoneuserservice.vo;

import lombok.Data;

@Data
public class ResponseCell {
    private String cellId;

    private Integer totalCell;
    private Integer liveCell;
    private Integer deadCell;
    private Double viability;
}
