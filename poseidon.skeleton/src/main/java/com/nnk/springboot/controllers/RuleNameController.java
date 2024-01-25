package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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
public class RuleNameController {
    @Autowired
    private RuleNameService ruleNameService;

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        model.addAttribute("ruleNames", ruleNameService.findAll());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        try {
            if (!result.hasErrors()) {
                ruleNameService.add(ruleName);
                return "redirect:/ruleName/list";
            }
        } catch (Exception e) {
            result.rejectValue("name", "error.ruleName", e.getMessage());
        }
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, BindingResult result) {
        try {
            RuleName ruleName = ruleNameService.findById(id);
            model.addAttribute("ruleName", ruleName);
        } catch (Exception e) {
            result.rejectValue("name", "error.ruleName", e.getMessage());
        }
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        try {
            if (!result.hasErrors()) {
                ruleNameService.update(id, ruleName);
                return "redirect:/ruleName/list";
            }
        } catch (Exception e) {
            result.rejectValue("name", "error.ruleName", e.getMessage());
        }
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        try {
            ruleNameService.deleteById(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/ruleName/list";
    }
}
