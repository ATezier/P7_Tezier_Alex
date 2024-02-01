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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class BidListController {
    @Autowired
    BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model, RedirectAttributes redirect)
    {
        String error = (String) model.getAttribute("error");
        if(error != null) model.addAttribute("error", error);
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
        } catch (IllegalArgumentException e) {
            result.rejectValue("account", "error.account", e.getMessage());
            return "redirect:/bidList/add";
        }
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirect) {
        try {
            BidList bidList = bidListService.findById(id);
            model.addAttribute("bidList", bidList);
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        try {
            bidListService.update(id, bidList);
        } catch (IllegalArgumentException e) {
            result.rejectValue("bidListId", "bidList.id", e.getMessage());
            return "redirect:/bidList/update";
        }
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model, RedirectAttributes redirect) {
        try {
            bidListService.deleteById(id);
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/bidList/list";
    }
}
