package com.nnk.springboot.domain;

import javax.persistence.*;

@Entity
@Table(name = "rating")
public class Rating {
    // TODO: Map columns in data table RATING with corresponding java fields
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;
    String moodysRating;
    String sandPRating;
    String fitchRating;
    Integer orderNumber;
}
