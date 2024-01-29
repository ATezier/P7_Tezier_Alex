package com.nnk.springboot.services;


import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
public class CurvePointTests {
    @MockBean
    CurvePointRepository curvePointRepository;
    @Autowired
    CurvePointService curvePointService;

    @Test
    public void curvePointTest() {
        CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
        Integer id = 1;
        curvePoint.setId(id);
        List<CurvePoint> curvePoints = new ArrayList<CurvePoint>();
        curvePoints.add(curvePoint);

        // Valid
        Assertions.assertTrue(curvePointService.valid(curvePoint));

        // Create
        given(curvePointRepository.findByCurveIdAndTermAndValue(
                curvePoint.getCurveId(), curvePoint.getTerm(), curvePoint.getValue())).willReturn(null);
        given(curvePointRepository.save(curvePoint)).willReturn(curvePoint);
        Assertions.assertNotNull(curvePointService.create(curvePoint));

        // Read
        given(curvePointRepository.findAll()).willReturn(curvePoints);
        Assertions.assertTrue(curvePointService.findAll().size() > 0);

        given(curvePointRepository.findById(id)).willReturn(Optional.of(curvePoint));
        Assertions.assertNotNull(curvePointService.findById(id));


        // Update
        Assertions.assertNotNull(curvePointService.update(id,
                new CurvePoint(curvePoint.getCurveId(), curvePoint.getTerm(), 40d)));

        // Delete
        Assertions.assertDoesNotThrow( () -> curvePointService.deleteById(id));
    }
}
