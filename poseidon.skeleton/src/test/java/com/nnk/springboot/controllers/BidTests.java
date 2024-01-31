package com.nnk.springboot.controllers;


import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BidTests {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @Autowired
    private BidListRepository bidListRepository;

    @BeforeAll
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @WithMockUser(username = "Tester", roles = "USER")
    public void bidListHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/list"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithMockUser(username = "Tester", roles = "USER")
    public void bidListAdd() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/add"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithMockUser(username = "Tester", roles = "USER")
    public void BidListCUD() throws Exception {
        BidList bid = new BidList("Account Test", "Type Test", 100d);

        // Create
        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate").with(csrf())
                .param("account", bid.getAccount())
                .param("type", bid.getType())
                .param("bidQuantity", bid.getBidQuantity().toString()))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/bidList/list"));
        bid = bidListRepository.findByAccountAndTypeAndBidQuantity(bid.getAccount(), bid.getType(), bid.getBidQuantity());

        // Update
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/update/" + bid.getBidListId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        bid.setBidQuantity(200d);
        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/update/" + bid.getBidListId())
                .param("account", bid.getAccount())
                .param("type", bid.getType())
                .param("bidQuantity", bid.getBidQuantity().toString()))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/bidList/list"));

        // Delete
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/delete/" + bid.getBidListId()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/bidList/list"));

    }
}
