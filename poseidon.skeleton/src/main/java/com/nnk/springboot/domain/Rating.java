package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rating")
@NoArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;
    @Column(name = "moodysRating")
    @Pattern(regexp = "[+]?([0-9]*[.])?[0-9]+", message = "The value must be a number")
    String moodysRating;
    @Column(name = "sandPRating")
    @Pattern(regexp = "[+]?([0-9]*[.])?[0-9]+", message = "The value must be a number")
    String sandPRating;
    @Column(name = "fitchRating")
    @Pattern(regexp = "[+]?([0-9]*[.])?[0-9]+", message = "The value must be a number")
    String fitchRating;
    @Column(name = "orderNumber")
    @Pattern(regexp = "[0-9]+", message = "The value must be a number")
    Integer orderNumber;

    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMoodysRating() {
        return moodysRating;
    }

    public void setMoodysRating(String moodysRating) {
        this.moodysRating = moodysRating;
    }

    public String getSandPRating() {
        return sandPRating;
    }

    public void setSandPRating(String sandPRating) {
        this.sandPRating = sandPRating;
    }

    public String getFitchRating() {
        return fitchRating;
    }

    public void setFitchRating(String fitchRating) {
        this.fitchRating = fitchRating;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
}
