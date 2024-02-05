package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "[0-9]+", message = "The value must be a number")
    Integer curveId;
    @Column(name = "asOfDate")
    Timestamp asOfDate;
    @Column(name = "term")
    @Pattern(regexp = "[+]?([0-9]*[.])?[0-9]+", message = "The value must be a number")
    Double term;
    @Column(name = "value")
    @Pattern(regexp = "[+]?([0-9]*[.])?[0-9]+", message = "The value must be a number")
    Double value;
    @Column(name = "creationDate")
    Timestamp creationDate;

    public CurvePoint(Integer curveId, Double term, Double value) {
        this.curveId = curveId;
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
