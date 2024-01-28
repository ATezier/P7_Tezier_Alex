package com.nnk.springboot.services;


import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RuleTests {
    @MockBean
    RuleNameRepository ruleNameRepository;
    @Autowired
    RuleNameService ruleNameService;
}
