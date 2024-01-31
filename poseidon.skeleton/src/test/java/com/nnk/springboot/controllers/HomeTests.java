package com.nnk.springboot.controllers;


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

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HomeTests {
    @Autowired
    private MockMvc mockMvc;
    @Test
    @WithMockUser(username = "Tester", roles = "USER")
    public void home() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithMockUser(username = "Tester", roles = "ADMIN")
    public void adminHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/home"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/bidList/list"));
    }
}
