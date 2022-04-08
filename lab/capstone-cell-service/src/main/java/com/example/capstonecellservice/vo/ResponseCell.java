package com.example.capstonecellservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCell {
    private String cellId;
    private Integer liveCellCount;
    private Integer dieCellCount;
    private Long liveCellPercentage;
    private Long dieCellPercentage;

    private Date createAt;
    private String userId;
}
