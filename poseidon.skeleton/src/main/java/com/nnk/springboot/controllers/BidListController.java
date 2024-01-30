package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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
public class BidListController {
    @Autowired
    BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        model.addAttribute("bidLists", bidListService.findAll());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        try {
            if (result.hasErrors()) {
                return "redirect:/bidList/add";
            }
            bidListService.create(bid);
            model.addAttribute("bidList", bidListService.findAll());
        } catch (IllegalArgumentException e) {
            result.rejectValue("id", "bidList.id", e.getMessage());
            return "redirect:/bidList/add";
        }
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            BidList bidList = bidListService.findById(id);
            model.addAttribute("bidList", bidList);
        } catch (IllegalArgumentException e) {
            model.addAttribute("bidList", bidListService.findAll());
            return "redirect:/bidList/list";
        }
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        try {
            bidListService.update(id, bidList);
        } catch (IllegalArgumentException e) {
            result.rejectValue("id", "bidList.id", e.getMessage());
            return "bidList/update";
        }
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        try {
            bidListService.deleteById(id);
            model.addAttribute("bidList", bidListService.findAll());
        } catch (IllegalArgumentException e) {
            //case where the id is not in the database
        }
        return "redirect:/bidList/list";
    }
}
