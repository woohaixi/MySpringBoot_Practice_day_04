package com.hwadee.springboot.service;

import com.hwadee.springboot.entity.SpecialEvent;
import com.hwadee.springboot.repository.SpecialEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SpecialEventService {
    
    @Autowired
    private SpecialEventRepository specialEventRepository;
    
    public List<SpecialEvent> getAllEvents() {
        return specialEventRepository.findAll();
    }
    
    public List<SpecialEvent> getActiveEvents() {
        return specialEventRepository.findByIsActiveTrue();
    }
    
    public List<SpecialEvent> getCurrentEvents() {
        return specialEventRepository.findActiveEventsByDate(LocalDateTime.now());
    }
    
    public List<SpecialEvent> getAvailableEvents() {
        return specialEventRepository.findAvailableEvents();
    }
    
    public Optional<SpecialEvent> getEventById(Long id) {
        return specialEventRepository.findById(id);
    }
    
    public SpecialEvent createEvent(SpecialEvent event) {
        return specialEventRepository.save(event);
    }
    
    public SpecialEvent updateEvent(Long id, SpecialEvent event) {
        Optional<SpecialEvent> existingEvent = specialEventRepository.findById(id);
        if (existingEvent.isPresent()) {
            SpecialEvent existing = existingEvent.get();
            existing.setEventName(event.getEventName());
            existing.setEventType(event.getEventType());
            existing.setSpecialPoints(event.getSpecialPoints());
            existing.setStartDate(event.getStartDate());
            existing.setEndDate(event.getEndDate());
            existing.setDescription(event.getDescription());
            existing.setIsActive(event.getIsActive());
            existing.setMaxParticipants(event.getMaxParticipants());
            return specialEventRepository.save(existing);
        }
        return null;
    }
    
    public void deleteEvent(Long id) {
        specialEventRepository.deleteById(id);
    }
    
    public boolean participateInEvent(Long eventId) {
        Optional<SpecialEvent> event = specialEventRepository.findById(eventId);
        if (event.isPresent()) {
            SpecialEvent existing = event.get();
            if (existing.getIsActive() && 
                existing.getCurrentParticipants() < existing.getMaxParticipants() &&
                existing.getStartDate().isBefore(LocalDateTime.now()) &&
                existing.getEndDate().isAfter(LocalDateTime.now())) {
                
                existing.setCurrentParticipants(existing.getCurrentParticipants() + 1);
                specialEventRepository.save(existing);
                return true;
            }
        }
        return false;
    }
}