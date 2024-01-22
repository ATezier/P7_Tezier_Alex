package com.nnk.springboot.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "rulename")
public class RuleName {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;
    @Column(name = "name")
    String name;
    @Column(name = "description")
    String description;
    @Column(name = "json")
    String json;
    @Column(name = "template")
    String template;
    @Column(name = "sqlStr")
    String sqlStr;
    @Column(name = "sqlPart")
    String sqlPart;

}
