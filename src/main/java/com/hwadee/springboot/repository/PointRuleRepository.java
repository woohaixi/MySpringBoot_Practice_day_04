package com.hwadee.springboot.repository;

import com.hwadee.springboot.entity.PointRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PointRuleRepository extends JpaRepository<PointRule, Long> {
    
    List<PointRule> findByIsActiveTrue();
    
    List<PointRule> findByActivityType(String activityType);
    
    List<PointRule> findByActivityTypeAndIsActiveTrue(String activityType, Boolean isActive);
    
    @Query("SELECT pr FROM PointRule pr WHERE pr.activityType = :activityType AND pr.isActive = true")
    List<PointRule> findActiveRulesByActivityType(@Param("activityType") String activityType);
}