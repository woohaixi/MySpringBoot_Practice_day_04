package com.hwadee.springboot.repository;

import com.hwadee.springboot.entity.SpecialEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SpecialEventRepository extends JpaRepository<SpecialEvent, Long> {
    
    List<SpecialEvent> findByIsActiveTrue();
    
    List<SpecialEvent> findByEventType(String eventType);
    
    @Query("SELECT se FROM SpecialEvent se WHERE se.isActive = true AND se.startDate <= :currentDate AND se.endDate >= :currentDate")
    List<SpecialEvent> findActiveEventsByDate(@Param("currentDate") LocalDateTime currentDate);
    
    @Query("SELECT se FROM SpecialEvent se WHERE se.isActive = true AND se.currentParticipants < se.maxParticipants")
    List<SpecialEvent> findAvailableEvents();
}