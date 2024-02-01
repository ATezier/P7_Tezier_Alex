package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public boolean valid(Rating rating) {
        boolean isValid = true;
        if (rating.getMoodysRating() == null || rating.getMoodysRating().isEmpty()) {
            isValid = false;
        }
        if (rating.getSandPRating() == null || rating.getSandPRating().isEmpty()) {
            isValid = false;
        }
        if (rating.getFitchRating() == null || rating.getFitchRating().isEmpty()) {
            isValid = false;
        }
        if (rating.getOrderNumber() == null || rating.getOrderNumber() < 0) {
            isValid = false;
        }
        return isValid;
    }

    public Rating findById(Integer id) {
        return ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Rating not found."));
    }

    public List<Rating> findAll() { return ratingRepository.findAll(); }

    public Rating create(Rating rating) {
        Rating res = null;
        if (valid(rating)) {
            res = ratingRepository.save(rating);
        } else {
            throw new IllegalArgumentException("Invalid rating");
        }
        return res;
    }

    public Rating update(Integer id, Rating rating) {
        Rating res = null;
        if (valid(rating)) {
            try {
                Rating ratingToUpdate = this.findById(id);
                ratingToUpdate.setMoodysRating(rating.getMoodysRating());
                ratingToUpdate.setSandPRating(rating.getSandPRating());
                ratingToUpdate.setFitchRating(rating.getFitchRating());
                ratingToUpdate.setOrderNumber(rating.getOrderNumber());
                res = ratingRepository.save(ratingToUpdate);
            } catch (Exception e) {
                throw new IllegalArgumentException("Rating not found, cannot be updated.");
            }
        } else {
            throw new IllegalArgumentException("Invalid rating");
        }
        return res;
    }

    public void deleteById(Integer id) {
        try {
            Rating rating = this.findById(id);
            ratingRepository.delete(rating);
        } catch (Exception e) {
            throw new IllegalArgumentException("Rating not found, cannot be deleted.");
        }
    }
}
