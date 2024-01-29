package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public boolean valid(User user) {
        boolean isValid = true;
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            isValid = false;
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            isValid = false;
        }
        if (user.getFullname() == null || user.getFullname().isEmpty()) {
            isValid = false;
        }
        if (user.getRole() == null || user.getRole().isEmpty()) {
            isValid = false;
        }
        return isValid;
    }
    public List<User> findAll() {return userRepository.findAll();}
    public User findById(Integer id) { return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id)); }

    public User findByUsername(String username) { return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + "doesn't exist")); }
    public void deleteById(Integer id) {
        try {
            User user = this.findById(id);
            userRepository.delete(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid user Id:" + id);
        }
    }

    public User create(User user) {
        User res = null;
        if (valid(user)) {
            try {
                this.findByUsername(user.getUsername());
                throw new IllegalArgumentException("User already exists");
            } catch (UsernameNotFoundException e) {
                res = userRepository.save(user);
            }
        } else {
            throw new IllegalArgumentException("Invalid user");
        }
        return res;
    }

    public User update(Integer id, User user) {
        User res = null;
        if (valid(user)) {
            try {
                User oldUser = this.findById(id);
                oldUser.setFullname(user.getFullname());
                oldUser.setPassword(user.getPassword());
                oldUser.setRole(user.getRole());
                oldUser.setUsername(user.getUsername());
                res = userRepository.save(oldUser);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid user Id:" + id);
            }
        } else {
            throw new IllegalArgumentException("Invalid user");
        }
        return res;
    }
    public void passwordValid(String password) {
        if(!password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            throw new IllegalArgumentException("Un mot de passe doit contenir au moins 8 caractères, une majuscule, un chiffre et un caractère spécial");
        }
    }
}
