package com.nnk.springboot.controllers;


import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
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
public class RuleTests {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @Autowired
    private RuleNameRepository ruleNameRepository;
    @BeforeAll
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    @WithMockUser(username = "Tester", roles = "USER")
    public void ruleNameHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/list"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithMockUser(username = "Tester", roles = "USER")
    public void ruleNameAddForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/add"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithMockUser(username = "Tester", roles = "USER")
    public void ruleNameCUD() throws Exception {
        RuleName ruleName = new RuleName("Name Test", "Description", "Json", "Template", "SQL", "SQL Part");

        // Create
        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/validate").with(csrf())
                        .param("name", ruleName.getName())
                        .param("description", ruleName.getDescription())
                        .param("json", ruleName.getJson())
                        .param("template", ruleName.getTemplate())
                        .param("sqlStr", ruleName.getSqlStr())
                        .param("sqlPart", ruleName.getSqlPart()))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/ruleName/list"));
        ruleName = ruleNameRepository.findByNameAndJsonAndTemplateAndSqlStrAndSqlPart(
                ruleName.getName(), ruleName.getJson(), ruleName.getTemplate(), ruleName.getSqlStr(), ruleName.getSqlPart());

        // Update
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/update/" + ruleName.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        ruleName.setName("Name Test Update");
        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/" + ruleName.getId())
                        .param("name", ruleName.getName())
                        .param("description", ruleName.getDescription())
                        .param("json", ruleName.getJson())
                        .param("template", ruleName.getTemplate())
                        .param("sqlStr", ruleName.getSqlStr())
                        .param("sqlPart", ruleName.getSqlPart()))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/ruleName/list"));

        // Delete
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/delete/" + ruleName.getId()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/ruleName/list"));
    }
}
