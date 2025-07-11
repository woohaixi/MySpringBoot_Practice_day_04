package com.hwadee.springboot.service;

import com.hwadee.springboot.entity.PointRule;
import com.hwadee.springboot.repository.PointRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PointRuleService {
    
    @Autowired
    private PointRuleRepository pointRuleRepository;
    
    public List<PointRule> getAllRules() {
        return pointRuleRepository.findAll();
    }
    
    public List<PointRule> getActiveRules() {
        return pointRuleRepository.findByIsActiveTrue();
    }
    
    public List<PointRule> getRulesByActivityType(String activityType) {
        return pointRuleRepository.findActiveRulesByActivityType(activityType);
    }
    
    public Optional<PointRule> getRuleById(Long id) {
        return pointRuleRepository.findById(id);
    }
    
    public PointRule createRule(PointRule rule) {
        return pointRuleRepository.save(rule);
    }
    
    public PointRule updateRule(Long id, PointRule rule) {
        Optional<PointRule> existingRule = pointRuleRepository.findById(id);
        if (existingRule.isPresent()) {
            PointRule existing = existingRule.get();
            existing.setRuleName(rule.getRuleName());
            existing.setActivityType(rule.getActivityType());
            existing.setBasePoints(rule.getBasePoints());
            existing.setBonusPoints(rule.getBonusPoints());
            existing.setDifficultyLevel(rule.getDifficultyLevel());
            existing.setDescription(rule.getDescription());
            existing.setIsActive(rule.getIsActive());
            return pointRuleRepository.save(existing);
        }
        return null;
    }
    
    public void deleteRule(Long id) {
        pointRuleRepository.deleteById(id);
    }
    
    public void deactivateRule(Long id) {
        Optional<PointRule> rule = pointRuleRepository.findById(id);
        if (rule.isPresent()) {
            PointRule existing = rule.get();
            existing.setIsActive(false);
            pointRuleRepository.save(existing);
        }
    }
}