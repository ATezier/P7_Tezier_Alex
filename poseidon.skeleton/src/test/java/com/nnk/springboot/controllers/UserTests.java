package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserTests {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @BeforeAll
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void userList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/list"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithMockUser(username = "Tester", roles = "USER")
    public void userAddForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/add"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithMockUser(username = "Tester", roles = "ADMIN")
    public void userCUD() throws Exception {
        User user = new User("Tester", "Password1*", "Fullname", "ADMIN");

        // Create
        mockMvc.perform(MockMvcRequestBuilders.post("/user/validate").with(csrf())
                        .param("username", user.getUsername())
                        .param("password", user.getPassword())
                        .param("fullname", user.getFullname())
                        .param("role", user.getRole()))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/list"));
        user = userRepository.findByUsername(user.getUsername()).get();

        // Update
        mockMvc.perform(MockMvcRequestBuilders.get("/user/update/" + user.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        user.setFullname("Fullname TEST");
        mockMvc.perform(MockMvcRequestBuilders.post("/user/update/" + user.getId())
                        .param("username", user.getUsername())
                        .param("fullname", user.getFullname())
                        .param("password", "Password1*")
                        .param("role", user.getRole()))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/list"));

        // Delete
        mockMvc.perform(MockMvcRequestBuilders.get("/user/delete/" + user.getId()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/list"));
    }
}
