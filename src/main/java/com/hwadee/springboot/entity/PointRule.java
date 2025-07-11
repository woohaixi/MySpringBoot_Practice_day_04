package com.hwadee.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "point_rules")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointRule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "rule_name", nullable = false)
    private String ruleName;
    
    @Column(name = "activity_type", nullable = false)
    private String activityType;
    
    @Column(name = "base_points", nullable = false)
    private Integer basePoints;
    
    @Column(name = "bonus_points")
    private Integer bonusPoints;
    
    @Column(name = "difficulty_level")
    private String difficultyLevel;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}