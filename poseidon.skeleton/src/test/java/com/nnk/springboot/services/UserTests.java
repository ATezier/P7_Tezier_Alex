package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserTests {
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Test
    public void UserTest() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User("test", encoder.encode("password"), "Mr. Test", "USER");
        Integer id = 1;
        user.setId(id);
        List<User> users = new ArrayList<User>();
        users.add(user);

        // Valid
        Assertions.assertTrue(userService.valid(user));

        // Create
        given(userRepository.save(user)).willReturn(user);
        Assertions.assertNotNull(userService.create(user));

        // Read
        given(userRepository.findAll()).willReturn(users);
        Assertions.assertTrue(userService.findAll().size() > 0);

        given(userRepository.findById(id)).willReturn(Optional.of(user));
        Assertions.assertNotNull(userService.findById(id));


        // Update
        Assertions.assertNotNull(userService.update(id, new User(
            user.getUsername(), user.getPassword(), user.getFullname(), user.getRole())));

        // Delete
        Assertions.assertDoesNotThrow( () -> userService.deleteById(id));
    }
}
