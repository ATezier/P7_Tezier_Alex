package com.nnk.springboot.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "bidlist")
public class BidList {
    // TODO: Map columns in data table BIDLIST with corresponding java fields
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer bidListId;
    String account;
    Double bidQuantity;
    Double bid;
    Double ask;
    String benchmark;
    Timestamp bidListDate;
    String commentary;
    String security;
    String status;
    String trader;
    String book;
    String creationName;
    Timestamp creationDate;
    String revisionName;
    Timestamp revisionDate;
    String dealName;
    String dealType;
    String sourceListId;
    String side;
    Double askQuantity;
}
