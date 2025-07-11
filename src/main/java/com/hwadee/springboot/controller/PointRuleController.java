package com.hwadee.springboot.controller;

import com.hwadee.springboot.entity.PointRule;
import com.hwadee.springboot.service.PointRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/point-rules")
@CrossOrigin(origins = "*")
public class PointRuleController {
    
    @Autowired
    private PointRuleService pointRuleService;
    
    @GetMapping
    public ResponseEntity<List<PointRule>> getAllRules() {
        List<PointRule> rules = pointRuleService.getAllRules();
        return ResponseEntity.ok(rules);
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<PointRule>> getActiveRules() {
        List<PointRule> rules = pointRuleService.getActiveRules();
        return ResponseEntity.ok(rules);
    }
    
    @GetMapping("/activity/{activityType}")
    public ResponseEntity<List<PointRule>> getRulesByActivityType(@PathVariable String activityType) {
        List<PointRule> rules = pointRuleService.getRulesByActivityType(activityType);
        return ResponseEntity.ok(rules);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PointRule> getRuleById(@PathVariable Long id) {
        Optional<PointRule> rule = pointRuleService.getRuleById(id);
        return rule.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<PointRule> createRule(@RequestBody PointRule rule) {
        PointRule createdRule = pointRuleService.createRule(rule);
        return ResponseEntity.ok(createdRule);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PointRule> updateRule(@PathVariable Long id, @RequestBody PointRule rule) {
        PointRule updatedRule = pointRuleService.updateRule(id, rule);
        return updatedRule != null ? ResponseEntity.ok(updatedRule) : ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        pointRuleService.deleteRule(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateRule(@PathVariable Long id) {
        pointRuleService.deactivateRule(id);
        return ResponseEntity.noContent().build();
    }
}