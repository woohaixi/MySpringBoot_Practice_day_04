package com.hwadee.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "point_records")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    
    @Column(name = "rule_id", nullable = false)
    private Long ruleId;
    
    @Column(name = "activity_name", nullable = false)
    private String activityName;
    
    @Column(name = "points_earned", nullable = false)
    private Integer pointsEarned;
    
    @Column(name = "points_type")
    private String pointsType; // BASE, BONUS, SPECIAL
    
    @Column(name = "earned_at")
    private LocalDateTime earnedAt;
    
    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;
    
    @Column(name = "is_expired")
    private Boolean isExpired = false;
    
    @Column(name = "is_used")
    private Boolean isUsed = false;
    
    @Column(name = "used_at")
    private LocalDateTime usedAt;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        earnedAt = LocalDateTime.now();
        // Set expiry date to 1 year from now by default
        expiryDate = LocalDateTime.now().plusYears(1);
    }
}