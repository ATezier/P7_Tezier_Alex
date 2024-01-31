package com.nnk.springboot.controllers;


import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
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
public class RatingTests {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @Autowired
    private RatingRepository ratingRepository;
    @BeforeAll
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    @WithMockUser(username = "Tester", roles = "USER")
    public void ratingHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/list"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithMockUser(username = "Tester", roles = "USER")
    public void ratingAddForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/add"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithMockUser(username = "Tester", roles = "USER")
    public void ratingCUD() throws Exception {
        Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

        // Create
        mockMvc.perform(MockMvcRequestBuilders.post("/rating/validate").with(csrf())
                        .param("moodysRating", rating.getMoodysRating())
                        .param("sandPRating", rating.getSandPRating())
                        .param("fitchRating", rating.getFitchRating())
                        .param("orderNumber", String.valueOf(rating.getOrderNumber())))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/rating/list"));
        rating = ratingRepository.findAll().stream().filter(r -> r.getMoodysRating().equals("Moodys Rating")).findFirst().get();

        // Update
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/update/" + rating.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        rating.setMoodysRating("Moodys Rating Update");
        mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/" + rating.getId())
                        .param("moodysRating", rating.getMoodysRating())
                        .param("sandPRating", rating.getSandPRating())
                        .param("fitchRating", rating.getFitchRating())
                        .param("orderNumber", String.valueOf(rating.getOrderNumber())))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/rating/list"));

        // Delete
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/delete/" + rating.getId()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/rating/list"));
    }
}
