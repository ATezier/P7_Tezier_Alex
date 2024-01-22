package com.nnk.springboot.domain;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;
    @Column(name = "curveId")
    Integer curveId;
    @Column(name = "asOfDate")
    Timestamp asOfDate;
    @Column(name = "term")
    Double term;
    @Column(name = "value")
    Double value;
    @Column(name = "creationDate")
    Timestamp creationDate;

}
