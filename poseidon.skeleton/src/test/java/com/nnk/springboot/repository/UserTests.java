package com.nnk.springboot.repository;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserTest() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User("test", encoder.encode("password"), "Mr. Test", "USER");

        // Save
        user = userRepository.save(user);
        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals("test", user.getUsername());


        // Update
        user.setFullname("Ms. Test");
        user = userRepository.save(user);
        Assertions.assertEquals("Ms. Test", user.getFullname());

        // Find
        List<User> listResult = userRepository.findAll();
        Assertions.assertTrue(listResult.size() > 0);

        // Delete
        Integer id = user.getId();
        userRepository.deleteById(id);
        Optional<User> _user = userRepository.findById(id);
        Assertions.assertFalse(_user.isPresent());
    }
}
