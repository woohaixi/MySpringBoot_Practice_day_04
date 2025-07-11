package com.hwadee.springboot.repository;

import com.hwadee.springboot.entity.PointRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PointRecordRepository extends JpaRepository<PointRecord, Long> {
    
    List<PointRecord> findByUserId(Integer userId);
    
    List<PointRecord> findByUserIdAndIsExpiredFalse(Integer userId);
    
    List<PointRecord> findByUserIdAndIsUsedFalse(Integer userId);
    
    @Query("SELECT pr FROM PointRecord pr WHERE pr.userId = :userId AND pr.isExpired = false AND pr.isUsed = false")
    List<PointRecord> findAvailablePointsByUserId(@Param("userId") Integer userId);
    
    @Query("SELECT SUM(pr.pointsEarned) FROM PointRecord pr WHERE pr.userId = :userId AND pr.isExpired = false AND pr.isUsed = false")
    Integer getTotalAvailablePointsByUserId(@Param("userId") Integer userId);
    
    @Query("SELECT pr FROM PointRecord pr WHERE pr.expiryDate < :currentDate AND pr.isExpired = false")
    List<PointRecord> findExpiredPoints(@Param("currentDate") LocalDateTime currentDate);
    
    List<PointRecord> findByRuleId(Long ruleId);
}