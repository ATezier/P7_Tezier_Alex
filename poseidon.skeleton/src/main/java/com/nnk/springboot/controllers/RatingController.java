package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        model.addAttribute("ratings", ratingService.findAll());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        try {
            if (!result.hasErrors()) {
                ratingService.create(rating);
                return "redirect:/rating/list";
            }
        } catch (Exception e) {
            result.rejectValue("name", "error.rating", e.getMessage());
        }
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            Rating rating = ratingService.findById(id);
            model.addAttribute("rating", rating);
        } catch (Exception e) {
            //failed to find the rating by id
        }
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        try {
            if (!result.hasErrors()) {
                ratingService.update(id, rating);
                return "redirect:/rating/list";
            }
        } catch (Exception e) {
            result.rejectValue("name", "error.rating", e.getMessage());
        }
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        try {
            ratingService.deleteById(id);
        } catch (Exception e) {
            model.addAttribute("error", "The rating has not been deleted.");
        }
        return "redirect:/rating/list";
    }
}
