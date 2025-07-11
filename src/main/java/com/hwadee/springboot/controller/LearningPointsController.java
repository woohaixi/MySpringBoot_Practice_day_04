package com.hwadee.springboot.controller;

import com.hwadee.springboot.entity.PointRule;
import com.hwadee.springboot.entity.PointRecord;
import com.hwadee.springboot.entity.SpecialEvent;
import com.hwadee.springboot.service.PointRuleService;
import com.hwadee.springboot.service.PointRecordService;
import com.hwadee.springboot.service.SpecialEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/learning-points")
@CrossOrigin(origins = "*")
public class LearningPointsController {
    
    @Autowired
    private PointRuleService pointRuleService;
    
    @Autowired
    private PointRecordService pointRecordService;
    
    @Autowired
    private SpecialEventService specialEventService;
    
    @GetMapping("/dashboard/{userId}")
    public ResponseEntity<Map<String, Object>> getDashboard(@PathVariable Integer userId) {
        Map<String, Object> dashboard = new HashMap<>();
        
        // Get user's total available points
        Integer totalPoints = pointRecordService.getTotalAvailablePointsByUserId(userId);
        dashboard.put("totalPoints", totalPoints);
        
        // Get user's point history
        List<PointRecord> pointHistory = pointRecordService.getRecordsByUserId(userId);
        dashboard.put("pointHistory", pointHistory);
        
        // Get available rules
        List<PointRule> activeRules = pointRuleService.getActiveRules();
        dashboard.put("activeRules", activeRules);
        
        // Get current special events
        List<SpecialEvent> currentEvents = specialEventService.getCurrentEvents();
        dashboard.put("currentEvents", currentEvents);
        
        return ResponseEntity.ok(dashboard);
    }
    
    @GetMapping("/activity-types")
    public ResponseEntity<Map<String, String>> getActivityTypes() {
        Map<String, String> activityTypes = new HashMap<>();
        activityTypes.put("ONLINE_COURSE", "线上课程学习");
        activityTypes.put("OFFLINE_TRAINING", "线下培训参与");
        activityTypes.put("ACADEMIC_LECTURE", "学术讲座听讲");
        activityTypes.put("SELF_STUDY", "自主学习");
        activityTypes.put("GROUP_DISCUSSION", "小组讨论");
        activityTypes.put("ASSIGNMENT_SUBMISSION", "作业提交");
        activityTypes.put("QUIZ_COMPLETION", "测验完成");
        activityTypes.put("PEER_REVIEW", "同行评议");
        return ResponseEntity.ok(activityTypes);
    }
    
    @PostMapping("/initialize-demo-data")
    public ResponseEntity<String> initializeDemoData() {
        // Create sample point rules
        PointRule rule1 = new PointRule();
        rule1.setRuleName("线上课程完成");
        rule1.setActivityType("ONLINE_COURSE");
        rule1.setBasePoints(10);
        rule1.setBonusPoints(5);
        rule1.setDifficultyLevel("MEDIUM");
        rule1.setDescription("完成线上课程学习可获得基础积分10分，高质量完成可获得额外5分");
        rule1.setIsActive(true);
        pointRuleService.createRule(rule1);
        
        PointRule rule2 = new PointRule();
        rule2.setRuleName("线下培训参与");
        rule2.setActivityType("OFFLINE_TRAINING");
        rule2.setBasePoints(20);
        rule2.setBonusPoints(10);
        rule2.setDifficultyLevel("HIGH");
        rule2.setDescription("参与线下培训可获得基础积分20分，积极参与可获得额外10分");
        rule2.setIsActive(true);
        pointRuleService.createRule(rule2);
        
        PointRule rule3 = new PointRule();
        rule3.setRuleName("学术讲座听讲");
        rule3.setActivityType("ACADEMIC_LECTURE");
        rule3.setBasePoints(15);
        rule3.setBonusPoints(8);
        rule3.setDifficultyLevel("MEDIUM");
        rule3.setDescription("参加学术讲座可获得基础积分15分，提问或互动可获得额外8分");
        rule3.setIsActive(true);
        pointRuleService.createRule(rule3);
        
        // Create sample special event
        SpecialEvent event1 = new SpecialEvent();
        event1.setEventName("春季编程竞赛");
        event1.setEventType("COMPETITION");
        event1.setSpecialPoints(100);
        event1.setStartDate(LocalDateTime.now());
        event1.setEndDate(LocalDateTime.now().plusMonths(1));
        event1.setDescription("春季编程竞赛，获奖者可获得100积分奖励");
        event1.setIsActive(true);
        event1.setMaxParticipants(50);
        specialEventService.createEvent(event1);
        
        return ResponseEntity.ok("Demo data initialized successfully");
    }
}