package com.example.capstonecellservice.dto;

import lombok.Data;

@Data
public class FlaskResponseDto {
    private String totalCell;
    private String liveCell;
    private String deadCell;
    private String url;
}
