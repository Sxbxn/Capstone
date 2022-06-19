package com.example.capstonecellservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCell {

    private String cellId;

    private Integer totalCell;
    private Integer liveCell;
    private Integer deadCell;
    private Double viability;

    private Date createAt;
    private String url;
    private String userid;

}
