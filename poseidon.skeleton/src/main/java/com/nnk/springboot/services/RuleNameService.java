package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleNameService {
    private final RuleNameRepository ruleNameRepository;

    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    public boolean valid(RuleName ruleName) {
        boolean isValid = true;
        if (ruleName.getName() == null || ruleName.getName().isEmpty()) {
            isValid = false;
        }
        if (ruleName.getDescription() == null || ruleName.getDescription().isEmpty()) {
            isValid = false;
        }
        if (ruleName.getJson() == null || ruleName.getJson().isEmpty()) {
            isValid = false;
        }
        if (ruleName.getTemplate() == null || ruleName.getTemplate().isEmpty()) {
            isValid = false;
        }
        if (ruleName.getSqlStr() == null || ruleName.getSqlStr().isEmpty()) {
            isValid = false;
        }
        if (ruleName.getSqlPart() == null || ruleName.getSqlPart().isEmpty()) {
            isValid = false;
        }
        return isValid;
    }

    public List<RuleName> findAll() {
        return ruleNameRepository.findAll();
    }

    public RuleName findById(Integer id) {
        return ruleNameRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("Invalid rule name Id:" + id) );
    }

    public void add(RuleName ruleName) {
        if (this.valid(ruleName)) {
            if(ruleNameRepository.findByNameAndJsonAndTemplateAndSqlStrAndSqlPart(ruleName.getName(), ruleName.getJson(), ruleName.getTemplate(), ruleName.getSqlStr(), ruleName.getSqlPart()) == null) {
                ruleNameRepository.save(ruleName);
            } else {
                throw new IllegalArgumentException("Rule name already exists");
            }
        } else {
            throw new IllegalArgumentException("Invalid rule name");
        }
    }

    public void update(Integer id, RuleName ruleName) {
        if (this.valid(ruleName)) {
            RuleName ruleNameToUpdate = this.findById(id);
            ruleNameToUpdate.setName(ruleName.getName());
            ruleNameToUpdate.setDescription(ruleName.getDescription());
            ruleNameToUpdate.setJson(ruleName.getJson());
            ruleNameToUpdate.setTemplate(ruleName.getTemplate());
            ruleNameToUpdate.setSqlStr(ruleName.getSqlStr());
            ruleNameToUpdate.setSqlPart(ruleName.getSqlPart());
            ruleNameRepository.save(ruleNameToUpdate);
        } else {
            throw new IllegalArgumentException("Invalid rule name");
        }
    }

    public void deleteById(Integer id) {
        try {
            RuleName ruleName = this.findById(id);
            ruleNameRepository.delete(ruleName);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid rule name Id:" + id);
        }
    }
}
