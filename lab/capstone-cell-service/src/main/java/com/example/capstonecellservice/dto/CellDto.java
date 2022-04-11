package com.example.capstonecellservice.dto;

import lombok.Data;

@Data
public class CellDto {

    private String cellId;

    private Long totalCell;
    private Long liveCell;
    private Long deadCell;
    private Double viability;

    private String userId;
}
