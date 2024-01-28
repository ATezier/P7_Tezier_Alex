package com.nnk.springboot.services;


import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CurvePointTests {
    @MockBean
    CurvePointRepository curvePointRepository;
    @Autowired
    CurvePointService curvePointService;
}
