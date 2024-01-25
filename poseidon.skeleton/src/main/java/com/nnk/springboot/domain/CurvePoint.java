package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "curvepoint")
@NoArgsConstructor
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

    public CurvePoint(Integer id, Double term, Double value) {
        this.id = id;
        this.asOfDate = null;
        this.term = term;
        this.value = value;
        this.creationDate = null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCurveId() {
        return curveId;
    }

    public void setCurveId(Integer curveId) {
        this.curveId = curveId;
    }

    public Timestamp getAsOfDate() {
        return asOfDate;
    }

    public void setAsOfDate(Timestamp asOfDate) {
        this.asOfDate = asOfDate;
    }

    public Double getTerm() {
        return term;
    }

    public void setTerm(Double term) {
        this.term = term;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
}
