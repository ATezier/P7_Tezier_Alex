package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
    List<RuleName> findAll();
    RuleName save(RuleName ruleName);
    Optional<RuleName> findById(Integer id);
    void delete(RuleName ruleName);
    boolean updateById(Integer id, RuleName ruleName);
}
