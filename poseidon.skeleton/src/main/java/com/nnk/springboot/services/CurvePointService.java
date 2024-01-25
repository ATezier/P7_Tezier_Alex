package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CurvePointService {
    private final CurvePointRepository curvePointRepository;

    public CurvePointService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    public boolean valid(CurvePoint curvePoint) {
        boolean isValid = true;
        if (curvePoint.getCurveId() == null || curvePoint.getCurveId() < 0) {
            isValid = false;
        }
        if (curvePoint.getTerm() == null) {
            isValid = false;
        }
        if (curvePoint.getValue() == null) {
            isValid = false;
        }
        return isValid;
    }

    public List<CurvePoint> findAll() { return curvePointRepository.findAll(); }

    public CurvePoint findById(Integer id) {
        return curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
    }

    public void add(CurvePoint curvePoint) {
        if (valid(curvePoint)) {
            if(curvePointRepository.findByCurveIdAndTermAndValue(curvePoint.getCurveId(), curvePoint.getTerm(), curvePoint.getValue()) == null) {
                curvePoint.setCreationDate(new Timestamp(System.currentTimeMillis()));
                curvePointRepository.save(curvePoint);
            } else {
                throw new IllegalArgumentException("CurvePoint already exists");
            }
        } else {
            throw new IllegalArgumentException("Invalid CurvePoint");
        }
    }

    public void update(Integer id, CurvePoint curvePoint) {
        if (valid(curvePoint)) {
            try {
                CurvePoint curvePointToUpdate = this.findById(id);
                if(curvePoint.getCurveId() == curvePointToUpdate.getCurveId()
                        && curvePoint.getTerm() == curvePointToUpdate.getTerm()
                        && curvePoint.getValue() == curvePointToUpdate.getValue()) {
                    throw new IllegalArgumentException("CurvePoint already up to date");
                } else {
                    curvePointToUpdate.setAsOfDate(curvePoint.getAsOfDate());
                    curvePointRepository.save(curvePointToUpdate);
                }
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid Id");
            }
        } else {
            throw new IllegalArgumentException("Invalid CurvePoint");
        }
    }

    public void deleteById(Integer id) {
        try {
            CurvePoint curvePoint = this.findById(id);
            curvePointRepository.delete(curvePoint);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Id");
        }
    }
}
