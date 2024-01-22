package com.nnk.springboot.domain;

import javax.persistence.*;

@Entity
@Table(name = "rulename")
public class RuleName {
    // TODO: Map columns in data table RULENAME with corresponding java fields
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;
    String name;
    String description;
    String json;
    String template;
    String sqlStr;
    String sqlPart;

}
