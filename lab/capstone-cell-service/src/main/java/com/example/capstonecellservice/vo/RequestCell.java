package com.example.capstonecellservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestCell {

    private Long totalCell;
    private Long liveCell;
    private Long deadCell;

}
