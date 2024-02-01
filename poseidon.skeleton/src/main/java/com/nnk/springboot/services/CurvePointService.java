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
        return curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("CurvePoint not found"));
    }

    public CurvePoint create(CurvePoint curvePoint) {
        CurvePoint res = null;
        if(curvePoint.getCurveId() == null || curvePoint.getCurveId() == 0) {
            curvePoint.setCurveId(Math.toIntExact(curvePointRepository.count()) + 1);
        }
        if (valid(curvePoint)) {
            if(curvePointRepository.findByCurveIdAndTermAndValue(curvePoint.getCurveId(), curvePoint.getTerm(), curvePoint.getValue()) == null) {
                curvePoint.setCreationDate(new Timestamp(System.currentTimeMillis()));
                res = curvePointRepository.save(curvePoint);
            } else {
                throw new IllegalArgumentException("CurvePoint does already exist");
            }
        } else {
            throw new IllegalArgumentException("Invalid CurvePoint");
        }
        return res;
    }

    public CurvePoint update(Integer id, CurvePoint curvePoint) {
        CurvePoint res = null;
        if (valid(curvePoint)) {
            try {
                CurvePoint curvePointToUpdate = this.findById(id);
                if(curvePoint.getCurveId() == curvePointToUpdate.getCurveId()
                        && curvePoint.getTerm() == curvePointToUpdate.getTerm()
                        && curvePoint.getValue() == curvePointToUpdate.getValue()) {
                    throw new IllegalArgumentException("CurvePoint is already up to date");
                } else {
                    curvePointToUpdate.setCurveId(curvePoint.getCurveId());
                    curvePointToUpdate.setTerm(curvePoint.getTerm());
                    curvePointToUpdate.setValue(curvePoint.getValue());
                    res = curvePointRepository.save(curvePointToUpdate);
                }
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("CurvePoint not found, cannot be updated.");
            }
        } else {
            throw new IllegalArgumentException("Invalid CurvePoint");
        }
        return res;
    }

    public void deleteById(Integer id) {
        try {
            CurvePoint curvePoint = this.findById(id);
            curvePointRepository.delete(curvePoint);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("CurvePoint not found, cannot be deleted.");
        }
    }
}
