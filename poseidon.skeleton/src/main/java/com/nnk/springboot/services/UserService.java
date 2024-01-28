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
    public List<User> findAll() {return userRepository.findAll();}
    public User save(User user) {return userRepository.save(user);}
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
}
