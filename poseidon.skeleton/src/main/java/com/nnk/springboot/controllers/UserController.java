package com.nnk.springboot.controllers;

import com.nnk.springboot.config.SpringSecurityConfig;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
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

import java.util.Collection;

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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<? extends GrantedAuthority> roles = ((UserDetails) principal).getAuthorities();
        boolean isAdmin = false;
        for (GrantedAuthority role : roles) {
            if (role.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            }
        }
        if (isAdmin) {
            model.addAttribute("users", userService.findAll());
        } else {
            model.addAttribute("users", userService.findByUsername(((UserDetails) principal).getUsername()));
        }
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(User bid) {
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
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
        try {
            userService.passwordValid(user.getPassword());
            BCryptPasswordEncoder encoder = securityConfig.passwordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            user.setId(id);
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
        try {
            userService.deleteById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid user Id:" + id);
        }
        model.addAttribute("users", userService.findAll());
        return "redirect:/user/list";
    }
}
