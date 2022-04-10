package com.example.capstonecellservice.vo;

import lombok.Data;

@Data
// 사실 필요없는데 실험용으로 만들어 놓음
public class RequestCell {
    private Integer liveCellCount;
    private Integer dieCellCount;
}
