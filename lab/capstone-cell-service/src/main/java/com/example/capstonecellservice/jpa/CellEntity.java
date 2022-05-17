package com.example.capstonecellservice.jpa;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "cell")
public class CellEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String cellId;

    @Column(nullable = false)
    private Integer totalCell; // 전체 세포 수
    @Column(nullable = false)
    private Integer liveCell;  // 살아있는 세포 수
    @Column(nullable = false)
    private Integer deadCell;  // 죽은 세포 수
    @Column(nullable = false)
    private Double viability; // 몇 퍼센트의 세포가 생존해 있는지 나타내는 수치

//    @Column(nullable = false)
//    private String url; // 이미지 url

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createAt;
}
