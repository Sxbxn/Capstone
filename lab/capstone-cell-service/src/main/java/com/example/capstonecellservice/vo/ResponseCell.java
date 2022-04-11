package com.example.capstonecellservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCell {

    private String cellId;

    private Long totalCell;
    private Long liveCell;
    private Long deadCell;
    private Double viability;

    private Date createAt;
    private String userid;

}
