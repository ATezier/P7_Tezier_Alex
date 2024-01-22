package com.nnk.springboot.domain;

import jakarta.persistence.*;
@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;
    @Column(name = "moodysRating")
    String moodysRating;
    @Column(name = "sandPRating")
    String sandPRating;
    @Column(name = "fitchRating")
    String fitchRating;
    @Column(name = "orderNumber")
    Integer orderNumber;
}
