package com.example.capstonecellservice.jpa;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cells")
public class CellEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long totalCell; // 전체 세포 수
    private Long liveCell;  // 살아있는 세포 수
    private Long deadCell;  // 죽은 세포 수
    private Long viability; // 몇 퍼센트의 세포가 생존해 있는지 나타내는 수치

    private String userId;


}
