package com.example.capstonecellservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCell {

    private Long totalCell;
    private Long liveCell;
    private Long deadCell;
    private Long viability;
    private Long userid;

}
