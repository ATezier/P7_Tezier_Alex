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

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    SpringSecurityConfig securityConfig;

    @RequestMapping("/user")
    public String user() { return "Welcome user"; }

    @RequestMapping("/user/list")
    public String home(Model model)
    {
        UserDetails userDetails = userService.getUserDetailsFromSecurityContext(SecurityContextHolder.getContext());
        boolean isAdmin = userService.isAdmin(SecurityContextHolder.getContext());
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
            model.addAttribute("users", userService.findAll());
            return "redirect:/user/list";
        }
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            User user = userService.findById(id);
            user.setPassword("");
            model.addAttribute("user", user);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid user Id:" + id);
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
            result.rejectValue("username", "user.id", "You can't update another user");
            return "redirect:/user/update"+id;
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
                result.rejectValue("role", "user.role", "You can't update another user to admin");
                return "redirect:/user/update/"+id;
            }
            userService.update(id, user);
        } catch (Exception e) {
            result.rejectValue("password", "user.password", e.getMessage());
            return "redirect:/user/update/"+id;
        }
        model.addAttribute("users", userService.findAll());
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        boolean isAdmin = userService.isAdmin(SecurityContextHolder.getContext());
        boolean ownedId = userService.idVerifier(id, SecurityContextHolder.getContext());
        if (!isAdmin && !ownedId) {
            throw new IllegalArgumentException("You can't delete another user");
        }
        try {
            userService.deleteById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid user Id:" + id);
        }
        model.addAttribute("users", userService.findAll());
        return "redirect:/user/list";
    }
}
