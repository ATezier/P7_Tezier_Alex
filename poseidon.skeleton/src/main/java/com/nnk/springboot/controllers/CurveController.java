package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
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
public class CurveController {
    @Autowired
    private CurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model, RedirectAttributes redirect)
    {
        String error = (String) redirect.getAttribute("error");
        if (error != null) model.addAttribute("error", error);
        model.addAttribute("curvePoints", curvePointService.findAll());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        try {
            if (!result.hasErrors()) {
                curvePointService.create(curvePoint);
                return "redirect:/curvePoint/list";
            }
        } catch (IllegalArgumentException e) {
            result.rejectValue("term", "term", e.getMessage());
        }
        return "redirect:/curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirect) {
        try {
            CurvePoint curvePoint = curvePointService.findById(id);
            model.addAttribute("curvePoint", curvePoint);
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("error", e.getMessage());
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        try {
            if (!result.hasErrors()) {
                curvePointService.update(id, curvePoint);
            }
        } catch (IllegalArgumentException e) {
            result.rejectValue("curveId", "curvePoint.curveId", e.getMessage());
            return "redirect:/curvePoint/update";
        }
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model, RedirectAttributes redirect) {
        try {
            curvePointService.deleteById(id);
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/curvePoint/list";
    }
}
