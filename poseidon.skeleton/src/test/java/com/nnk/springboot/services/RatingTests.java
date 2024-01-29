package com.nnk.springboot.services;


import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RatingTests {
    @MockBean
    RatingRepository ratingRepository;
    @Autowired
    RatingService ratingService;

    @Test
    public void ratingTest() {
        Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        Integer id = 1;
        rating.setId(id);
        List<Rating> ratings = new ArrayList<Rating>();
        ratings.add(rating);

        // Valid
        Assertions.assertTrue(ratingService.valid(rating));

        // Create
        given(ratingRepository.save(rating)).willReturn(rating);
        Assertions.assertNotNull(ratingService.create(rating));

        // Read
        given(ratingRepository.findAll()).willReturn(ratings);
        Assertions.assertTrue(ratingService.findAll().size() > 0);

        given(ratingRepository.findById(id)).willReturn(Optional.of(rating));
        Assertions.assertNotNull(ratingService.findById(id));


        // Update
        Assertions.assertNotNull(ratingService.update(id, new Rating(
                rating.getMoodysRating(), rating.getSandPRating(), rating.getFitchRating(), 11)));

        // Delete
        Assertions.assertDoesNotThrow( () -> ratingService.deleteById(id));
    }
}
