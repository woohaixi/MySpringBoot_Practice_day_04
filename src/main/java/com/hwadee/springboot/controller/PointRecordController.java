package com.hwadee.springboot.controller;

import com.hwadee.springboot.entity.PointRecord;
import com.hwadee.springboot.service.PointRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/point-records")
@CrossOrigin(origins = "*")
public class PointRecordController {
    
    @Autowired
    private PointRecordService pointRecordService;
    
    @GetMapping
    public ResponseEntity<List<PointRecord>> getAllRecords() {
        List<PointRecord> records = pointRecordService.getAllRecords();
        return ResponseEntity.ok(records);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PointRecord>> getRecordsByUserId(@PathVariable Integer userId) {
        List<PointRecord> records = pointRecordService.getRecordsByUserId(userId);
        return ResponseEntity.ok(records);
    }
    
    @GetMapping("/user/{userId}/available")
    public ResponseEntity<List<PointRecord>> getAvailablePointsByUserId(@PathVariable Integer userId) {
        List<PointRecord> records = pointRecordService.getAvailablePointsByUserId(userId);
        return ResponseEntity.ok(records);
    }
    
    @GetMapping("/user/{userId}/total")
    public ResponseEntity<Integer> getTotalAvailablePointsByUserId(@PathVariable Integer userId) {
        Integer total = pointRecordService.getTotalAvailablePointsByUserId(userId);
        return ResponseEntity.ok(total);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PointRecord> getRecordById(@PathVariable Long id) {
        Optional<PointRecord> record = pointRecordService.getRecordById(id);
        return record.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/award")
    public ResponseEntity<PointRecord> awardPoints(@RequestBody Map<String, Object> request) {
        Integer userId = (Integer) request.get("userId");
        Long ruleId = Long.valueOf(request.get("ruleId").toString());
        String activityName = (String) request.get("activityName");
        String description = (String) request.get("description");
        
        PointRecord record = pointRecordService.awardPoints(userId, ruleId, activityName, description);
        return record != null ? ResponseEntity.ok(record) : ResponseEntity.badRequest().build();
    }
    
    @PostMapping("/award-special")
    public ResponseEntity<PointRecord> awardSpecialPoints(@RequestBody Map<String, Object> request) {
        Integer userId = (Integer) request.get("userId");
        String activityName = (String) request.get("activityName");
        Integer points = (Integer) request.get("points");
        String description = (String) request.get("description");
        LocalDateTime expiryDate = LocalDateTime.parse((String) request.get("expiryDate"));
        
        PointRecord record = pointRecordService.awardSpecialPoints(userId, activityName, points, description, expiryDate);
        return ResponseEntity.ok(record);
    }
    
    @PostMapping("/use")
    public ResponseEntity<Void> usePoints(@RequestBody Map<String, Object> request) {
        Integer userId = (Integer) request.get("userId");
        Integer pointsToUse = (Integer) request.get("pointsToUse");
        
        pointRecordService.usePoints(userId, pointsToUse);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/process-expired")
    public ResponseEntity<Void> processExpiredPoints() {
        pointRecordService.processExpiredPoints();
        return ResponseEntity.noContent().build();
    }
}