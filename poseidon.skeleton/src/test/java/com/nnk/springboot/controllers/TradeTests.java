package com.nnk.springboot.controllers;


import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
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
public class TradeTests {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @Autowired
    private TradeRepository tradeRepository;
    @BeforeAll
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    @WithMockUser(username = "Tester", roles = "USER")
    public void bidListHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/list"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithMockUser(username = "Tester", roles = "USER")
    public void bidListAdd() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/add"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithMockUser(username = "Tester", roles = "USER")
    public void BidListCUD() throws Exception {
        Trade trade = new Trade("Trade Account", "Type", 10d);

        // Create
        mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate").with(csrf())
                        .param("account", trade.getAccount())
                        .param("type", trade.getType())
                        .param("buyQuantity", trade.getBuyQuantity().toString()))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/trade/list"));
        trade = tradeRepository.findAll().stream().reduce((a, b) -> b).orElse(null);

        // Update
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/update/" + trade.getTradeId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        trade.setAccount("Trade Account Update");
        mockMvc.perform(MockMvcRequestBuilders.post("/trade/update/" + trade.getTradeId())
                        .param("account", trade.getAccount())
                        .param("type", trade.getType())
                        .param("buyQuantity", trade.getBuyQuantity().toString()))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/trade/list"));

        // Delete
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/delete/" + trade.getTradeId()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/trade/list"));
    }
}
