package com.nnk.springboot.controllers;

import com.nnk.springboot.config.SpringSecurityConfig;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    SpringSecurityConfig securityConfig;

    @RequestMapping("/user")
    public String user() { return "Welcome user"; }

    @RequestMapping("/user/list")
    public String home(Model model, RedirectAttributes redirect)
    {
        UserDetails userDetails = userService.getUserDetailsFromSecurityContext(SecurityContextHolder.getContext());
        boolean isAdmin = userService.isAdmin(SecurityContextHolder.getContext());
        String error = (String) redirect.getFlashAttributes().get("error");
        if (error != null) model.addAttribute("error", error);
        model.addAttribute("displayAddBtn", isAdmin);
        if (isAdmin) {
            model.addAttribute("users", userService.findAll());
        } else {
            model.addAttribute("users", userService.findByUsername(userDetails.getUsername()));
        }
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(User bid) {
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        boolean isAdmin = userService.isAdmin(SecurityContextHolder.getContext());
        if (!result.hasErrors() && isAdmin) {
            try {
                userService.passwordValid(user.getPassword());
                BCryptPasswordEncoder encoder = securityConfig.passwordEncoder();
                user.setPassword(encoder.encode(user.getPassword()));
                userService.create(user);
            } catch (Exception e) {
                result.rejectValue("password", "user.password", e.getMessage());
                return "user/add";
            }
            return "redirect:/user/list";
        }
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirect) {
        try {
            User user = userService.findById(id);
            user.setPassword("");
            model.addAttribute("user", user);
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/list";
        }
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/user/update/"+id;
        }
        boolean isAdmin = userService.isAdmin(SecurityContextHolder.getContext());
        boolean ownedId = userService.idVerifier(id, SecurityContextHolder.getContext());
        if (!isAdmin && !ownedId) {
            model.addAttribute("error", "You don't have the right to update another user");
            return "redirect:/user/list";
        }
        try {
            if(user.getPassword().isEmpty()) {
                user.setPassword(userService.findById(id).getPassword());
            } else {
                userService.passwordValid(user.getPassword());
                BCryptPasswordEncoder encoder = securityConfig.passwordEncoder();
                user.setPassword(encoder.encode(user.getPassword()));
            }
            user.setId(id);
            if(user.getRole().equals("ADMIN") && !isAdmin) {
                result.rejectValue("error", "You don't have the right to update a user to ADMIN");
                return "redirect:/user/list";
            }
            userService.update(id, user);
        } catch (Exception e) {
            result.rejectValue("password", "user.password", e.getMessage());
            return "redirect:/user/update/"+id;
        }
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model, RedirectAttributes redirect) {
        boolean isAdmin = userService.isAdmin(SecurityContextHolder.getContext());
        boolean ownedId = userService.idVerifier(id, SecurityContextHolder.getContext());
        if (!isAdmin && !ownedId) {
            redirect.addFlashAttribute("error", "You don't have the right to delete another user");
        } else {
            try {
                userService.deleteById(id);
            } catch (Exception e) {
                redirect.addFlashAttribute("error", e.getMessage());
            }
        }
        return "redirect:/user/list";
    }
}
