package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "bidlist")
@NoArgsConstructor
public class BidList {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer bidListId;
    @Column(name = "account")
    String account;
    @Column(name = "type")
    String type;
    @Column(name = "bidQuantity")
    @Pattern(regexp = "[+]?([0-9]*[.])?[0-9]+", message = "The value must be a number")
    Double bidQuantity;
    @Column(name = "bid")
    @Pattern(regexp = "[+]?([0-9]*[.])?[0-9]+", message = "The value must be a number")
    Double bid;
    @Column(name = "askQuantity")
    @Pattern(regexp = "[+]?([0-9]*[.])?[0-9]+", message = "The value must be a number")
    Double askQuantity;
    @Column(name = "ask")
    @Pattern(regexp = "[+]?([0-9]*[.])?[0-9]+", message = "The value must be a number")
    Double ask;
    @Column(name = "benchmark")
    String benchmark;
    @Column(name = "bidListDate")
    Timestamp bidListDate;
    @Column(name = "commentary")
    String commentary;
    @Column(name = "security")
    String security;
    @Column(name = "status")
    String status;
    @Column(name = "trader")
    String trader;
    @Column(name = "book")
    String book;
    @Column(name = "creationName")
    String creationName;
    @Column(name = "creationDate")
    Timestamp creationDate;
    @Column(name = "revisionName")
    String revisionName;
    @Column(name = "revisionDate")
    Timestamp revisionDate;
    @Column(name = "dealName")
    String dealName;
    @Column(name = "dealType")
    String dealType;
    @Column(name = "sourceListId")
    String sourceListId;
    @Column(name = "side")
    String side;

    public BidList(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
        this.bid = 0.0;
        this.askQuantity = 0.0;
        this.ask = 0.0;
        this.benchmark = null;
        this.bidListDate = null;
        this.commentary = null;
        this.security = null;
        this.status = null;
        this.trader = null;
        this.book = null;
        this.creationName = null;
        this.creationDate = null;
        this.revisionName = null;
        this.revisionDate = null;
        this.dealName = null;
        this.dealType = null;
        this.sourceListId = null;
        this.side = null;
    }

    public Integer getBidListId() {
        return bidListId;
    }

    public void setBidListId(Integer bidListId) {
        this.bidListId = bidListId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public Double getBidQuantity() {
        return bidQuantity;
    }

    public void setBidQuantity(Double bidQuantity) {
        this.bidQuantity = bidQuantity;
    }

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    public Double getAskQuantity() {
        return askQuantity;
    }

    public void setAskQuantity(Double askQuantity) {
        this.askQuantity = askQuantity;
    }

    public Double getAsk() {
        return ask;
    }

    public void setAsk(Double ask) {
        this.ask = ask;
    }

    public String getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(String benchmark) {
        this.benchmark = benchmark;
    }

    public Timestamp getBidListDate() {
        return bidListDate;
    }

    public void setBidListDate(Timestamp bidListDate) {
        this.bidListDate = bidListDate;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getCreationName() {
        return creationName;
    }

    public void setCreationName(String creationName) {
        this.creationName = creationName;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getRevisionName() {
        return revisionName;
    }

    public void setRevisionName(String revisionName) {
        this.revisionName = revisionName;
    }

    public Timestamp getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Timestamp revisionDate) {
        this.revisionDate = revisionDate;
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public String getSourceListId() {
        return sourceListId;
    }

    public void setSourceListId(String sourceListId) {
        this.sourceListId = sourceListId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
}
