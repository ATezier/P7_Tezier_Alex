package com.nnk.springboot.services;


import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RuleTests {
    @MockBean
    RuleNameRepository ruleNameRepository;
    @Autowired
    RuleNameService ruleNameService;

    @Test
    public void ruleNameTest() {
        RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        Integer id = 1;
        rule.setId(id);
        List<RuleName> ruleNames = new ArrayList<RuleName>();
        ruleNames.add(rule);

        // Valid
        Assertions.assertTrue(ruleNameService.valid(rule));

        // Create
        given(ruleNameRepository.save(rule)).willReturn(rule);
        Assertions.assertNotNull(ruleNameService.create(rule));

        // Read
        given(ruleNameRepository.findAll()).willReturn(ruleNames);
        Assertions.assertTrue(ruleNameService.findAll().size() > 0);

        given(ruleNameRepository.findById(id)).willReturn(Optional.of(rule));
        Assertions.assertNotNull(ruleNameService.findById(id));


        // Update
        Assertions.assertNotNull(ruleNameService.update(id, new RuleName(
                rule.getName(), rule.getDescription(), rule.getJson(), rule.getTemplate(), rule.getSqlStr(), "SQL PART")));

        // Delete
        Assertions.assertDoesNotThrow( () -> ruleNameService.deleteById(id));
    }
}
