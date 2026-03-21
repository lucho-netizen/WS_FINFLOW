package com.softdevcode.ws_finflow.controller;

import com.softdevcode.ws_finflow.dto.ContributionRequest;
import com.softdevcode.ws_finflow.dto.GoalRequest;
import com.softdevcode.ws_finflow.entity.Goal;
import com.softdevcode.ws_finflow.entity.GoalContribution;
import com.softdevcode.ws_finflow.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goals")
@CrossOrigin(origins = "*")
public class GoalController {

    @Autowired
    private GoalService service;

    // GET /goals
    @GetMapping
    public ResponseEntity<List<Goal>> getAll(@RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(service.getAll(userId));
    }

    // GET /goals/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Goal> getById(@RequestHeader("X-User-Id") Long userId, @PathVariable Long id) {
        return ResponseEntity.ok(service.getById(userId, id));
    }

    // POST /goals
    @PostMapping
    public ResponseEntity<Goal> create(@RequestHeader("X-User-Id") Long userId, @RequestBody GoalRequest request) {
        return ResponseEntity.ok(service.create(userId, request));
    }

    // PUT /goals/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Goal> update(@RequestHeader("X-User-Id") Long userId, @PathVariable Long id, @RequestBody GoalRequest request) {
        return ResponseEntity.ok(service.update(userId, id, request));
    }

    // PATCH /goals/{id}/contribute
    @PatchMapping("/{id}/contribute")
    public ResponseEntity<Goal> addContribution(@RequestHeader("X-User-Id") Long userId, @PathVariable Long id, @RequestBody ContributionRequest request) {
        return ResponseEntity.ok(service.addContribution(userId, id, request));
    }

    // GET /goals/{id}/contributions — historial de abonos
    @GetMapping("/{id}/contributions")
    public ResponseEntity<List<GoalContribution>> getContributions(@RequestHeader("X-User-Id") Long userId, @PathVariable Long id) {
        return ResponseEntity.ok(service.getContributions(userId, id));
    }

    // PATCH /goals/{id}/status
    @PatchMapping("/{id}/status")
    public ResponseEntity<Goal> updateStatus(@RequestHeader("X-User-Id") Long userId, @PathVariable Long id, @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(service.updateStatus(userId, id, body.get("status")));
    }

    // DELETE /goals/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@RequestHeader("X-User-Id") Long userId, @PathVariable Long id) {
        service.delete(userId, id);
        return ResponseEntity.noContent().build();
    }
}

