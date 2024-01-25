package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;


public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {
    List<CurvePoint> findAll();
    CurvePoint save(CurvePoint curvePoint);
    Optional<CurvePoint> findById(Integer id);
    void delete(CurvePoint curvePoint);
    CurvePoint findByCurveIdAndTermAndValue(@NonNull Integer curveId, @NonNull Double term, @NonNull Double value);
}
