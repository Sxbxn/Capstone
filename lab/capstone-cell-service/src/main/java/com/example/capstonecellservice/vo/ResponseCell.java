package com.example.capstonecellservice.vo;

import lombok.Data;

@Data
public class ResponseCell {

    private Long totalCell;
    private Long liveCell;
    private Long deadCell;
    private Long viability;

}
