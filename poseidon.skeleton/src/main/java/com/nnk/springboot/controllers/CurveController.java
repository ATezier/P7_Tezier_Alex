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

@Controller
public class CurveController {
    @Autowired
    private CurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        model.addAttribute("curvePoint", curvePointService.findAll());
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
                curvePointService.add(curvePoint);
                model.addAttribute("curvePoint", curvePointService.findAll());
                return "redirect:/curvePoint/list";
            }
        } catch (IllegalArgumentException e) {
            result.rejectValue("curveId", "curvePoint.curveId", e.getMessage());
        }
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, BindingResult result) {
        try {
            CurvePoint curvePoint = curvePointService.findById(id);
            model.addAttribute("curvePoint", curvePoint);
        } catch (IllegalArgumentException e) {
            result.rejectValue("curveId", "curvePoint.curveId", e.getMessage());
        }
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        try {
            if (!result.hasErrors()) {
                curvePointService.update(id, curvePoint);
                model.addAttribute("curvePoint", curvePointService.findAll());
                return "redirect:/curvePoint/list";
            }
        } catch (IllegalArgumentException e) {
            result.rejectValue("curveId", "curvePoint.curveId", e.getMessage());
        }
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model, BindingResult result) {
        try {
            curvePointService.deleteById(id);
            model.addAttribute("curvePoint", curvePointService.findAll());
        } catch (IllegalArgumentException e) {
            result.rejectValue("curveId", "curvePoint.curveId", e.getMessage());
        }
        return "redirect:/curvePoint/list";
    }
}
