package com.hwadee.springboot.controller;

import com.hwadee.springboot.entity.SpecialEvent;
import com.hwadee.springboot.service.SpecialEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/special-events")
@CrossOrigin(origins = "*")
public class SpecialEventController {
    
    @Autowired
    private SpecialEventService specialEventService;
    
    @GetMapping
    public ResponseEntity<List<SpecialEvent>> getAllEvents() {
        List<SpecialEvent> events = specialEventService.getAllEvents();
        return ResponseEntity.ok(events);
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<SpecialEvent>> getActiveEvents() {
        List<SpecialEvent> events = specialEventService.getActiveEvents();
        return ResponseEntity.ok(events);
    }
    
    @GetMapping("/current")
    public ResponseEntity<List<SpecialEvent>> getCurrentEvents() {
        List<SpecialEvent> events = specialEventService.getCurrentEvents();
        return ResponseEntity.ok(events);
    }
    
    @GetMapping("/available")
    public ResponseEntity<List<SpecialEvent>> getAvailableEvents() {
        List<SpecialEvent> events = specialEventService.getAvailableEvents();
        return ResponseEntity.ok(events);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SpecialEvent> getEventById(@PathVariable Long id) {
        Optional<SpecialEvent> event = specialEventService.getEventById(id);
        return event.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<SpecialEvent> createEvent(@RequestBody SpecialEvent event) {
        SpecialEvent createdEvent = specialEventService.createEvent(event);
        return ResponseEntity.ok(createdEvent);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SpecialEvent> updateEvent(@PathVariable Long id, @RequestBody SpecialEvent event) {
        SpecialEvent updatedEvent = specialEventService.updateEvent(id, event);
        return updatedEvent != null ? ResponseEntity.ok(updatedEvent) : ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        specialEventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{id}/participate")
    public ResponseEntity<String> participateInEvent(@PathVariable Long id) {
        boolean success = specialEventService.participateInEvent(id);
        return success ? 
            ResponseEntity.ok("Successfully participated in event") :
            ResponseEntity.badRequest().body("Cannot participate in event");
    }
}