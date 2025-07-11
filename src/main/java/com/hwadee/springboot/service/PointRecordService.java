package com.hwadee.springboot.service;

import com.hwadee.springboot.entity.PointRecord;
import com.hwadee.springboot.entity.PointRule;
import com.hwadee.springboot.repository.PointRecordRepository;
import com.hwadee.springboot.repository.PointRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PointRecordService {
    
    @Autowired
    private PointRecordRepository pointRecordRepository;
    
    @Autowired
    private PointRuleRepository pointRuleRepository;
    
    public List<PointRecord> getAllRecords() {
        return pointRecordRepository.findAll();
    }
    
    public List<PointRecord> getRecordsByUserId(Integer userId) {
        return pointRecordRepository.findByUserId(userId);
    }
    
    public List<PointRecord> getAvailablePointsByUserId(Integer userId) {
        return pointRecordRepository.findAvailablePointsByUserId(userId);
    }
    
    public Integer getTotalAvailablePointsByUserId(Integer userId) {
        Integer total = pointRecordRepository.getTotalAvailablePointsByUserId(userId);
        return total != null ? total : 0;
    }
    
    public PointRecord awardPoints(Integer userId, Long ruleId, String activityName, String description) {
        Optional<PointRule> rule = pointRuleRepository.findById(ruleId);
        if (rule.isPresent() && rule.get().getIsActive()) {
            PointRule pointRule = rule.get();
            
            PointRecord record = new PointRecord();
            record.setUserId(userId);
            record.setRuleId(ruleId);
            record.setActivityName(activityName);
            record.setPointsEarned(pointRule.getBasePoints() + (pointRule.getBonusPoints() != null ? pointRule.getBonusPoints() : 0));
            record.setPointsType("BASE");
            record.setDescription(description);
            
            return pointRecordRepository.save(record);
        }
        return null;
    }
    
    public PointRecord awardSpecialPoints(Integer userId, String activityName, Integer points, String description, LocalDateTime expiryDate) {
        PointRecord record = new PointRecord();
        record.setUserId(userId);
        record.setActivityName(activityName);
        record.setPointsEarned(points);
        record.setPointsType("SPECIAL");
        record.setDescription(description);
        record.setExpiryDate(expiryDate);
        
        return pointRecordRepository.save(record);
    }
    
    public void usePoints(Integer userId, Integer pointsToUse) {
        List<PointRecord> availablePoints = pointRecordRepository.findAvailablePointsByUserId(userId);
        
        int remainingPoints = pointsToUse;
        for (PointRecord record : availablePoints) {
            if (remainingPoints <= 0) break;
            
            if (record.getPointsEarned() <= remainingPoints) {
                record.setIsUsed(true);
                record.setUsedAt(LocalDateTime.now());
                remainingPoints -= record.getPointsEarned();
                pointRecordRepository.save(record);
            }
        }
    }
    
    public void processExpiredPoints() {
        List<PointRecord> expiredPoints = pointRecordRepository.findExpiredPoints(LocalDateTime.now());
        for (PointRecord record : expiredPoints) {
            record.setIsExpired(true);
            pointRecordRepository.save(record);
        }
    }
    
    public Optional<PointRecord> getRecordById(Long id) {
        return pointRecordRepository.findById(id);
    }
}