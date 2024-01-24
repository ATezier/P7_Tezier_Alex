package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<User> findAll() {return userRepository.findAll();}
    public User save(User user) {return userRepository.save(user);}
    public User findById(Integer id) {return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));}
    public void delete(User user) {userRepository.delete(user);}
}
