package com.nnk.springboot.controllers;


import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
public class CurvePointTests {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @Autowired
    private CurvePointRepository curvePointRepository;
    @BeforeAll
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    @WithMockUser(username = "Tester", roles = "USER")
    public void curvePointList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/list"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithMockUser(username = "Tester", roles = "USER")
    public void curvePointAddForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/add"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithMockUser(username = "Tester", roles = "USER")
    public void curvePointCUD() throws Exception {
        CurvePoint curvePoint = new CurvePoint(1, 10d, 30d);

        // Create
        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/validate").with(csrf())
                        .param("curveId", String.valueOf(curvePoint.getCurveId()))
                        .param("term", String.valueOf(curvePoint.getTerm()))
                        .param("value", String.valueOf(curvePoint.getValue())))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/curvePoint/list"));
        curvePoint = curvePointRepository.findByCurveIdAndTermAndValue(curvePoint.getCurveId(), curvePoint.getTerm(), curvePoint.getValue());

        // Update
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/update/" + curvePoint.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        curvePoint.setTerm(20d);
        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/" + curvePoint.getId()).with(csrf())
                        .param("curveId", String.valueOf(curvePoint.getCurveId()))
                        .param("term", String.valueOf(curvePoint.getTerm()))
                        .param("value", String.valueOf(curvePoint.getValue())))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/curvePoint/list"));

        // Delete
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/delete/" + curvePoint.getId()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/curvePoint/list"));
    }
}
