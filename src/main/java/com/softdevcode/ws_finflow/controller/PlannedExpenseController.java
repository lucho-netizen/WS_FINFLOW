package com.softdevcode.ws_finflow.controller;

import com.softdevcode.ws_finflow.dto.PlannedExpenseRequest;
import com.softdevcode.ws_finflow.entity.PlannedExpense;
import com.softdevcode.ws_finflow.service.PlannedExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planned-expenses")
@CrossOrigin(origins = "*")
public class PlannedExpenseController {

    @Autowired
    private PlannedExpenseService service;

    // GET /planned-expenses
    @GetMapping
    public ResponseEntity<List<PlannedExpense>> getAll(
            @RequestHeader("X-User-Id") Integer userId) {
        return ResponseEntity.ok(service.getAll(userId));
    }

    // POST /planned-expenses
    @PostMapping
    public ResponseEntity<PlannedExpense> create(
            @RequestHeader("X-User-Id") Integer userId,
            @RequestBody PlannedExpenseRequest req) {
        return ResponseEntity.ok(service.create(userId, req));
    }

    // PUT /planned-expenses/{id}
    @PutMapping("/{id}")
    public ResponseEntity<PlannedExpense> update(
            @RequestHeader("X-User-Id") Integer userId,
            @PathVariable Long id,
            @RequestBody PlannedExpenseRequest req) {
        return ResponseEntity.ok(service.update(userId, id, req));
    }

    // PATCH /planned-expenses/{id}/execute — marca como hecho y crea transacción
    @PatchMapping("/{id}/execute")
    public ResponseEntity<PlannedExpense> execute(
            @RequestHeader("X-User-Id") Integer userId,
            @PathVariable Long id) {
        return ResponseEntity.ok(service.execute(userId, id));
    }

    // PATCH /planned-expenses/{id}/cancel
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<PlannedExpense> cancel(
            @RequestHeader("X-User-Id") Integer userId,
            @PathVariable Long id) {
        return ResponseEntity.ok(service.cancel(userId, id));
    }

    // DELETE /planned-expenses/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @RequestHeader("X-User-Id") Integer userId,
            @PathVariable Long id) {
        service.delete(userId, id);
        return ResponseEntity.noContent().build();
    }

    // GET /planned-expenses/overdue — gastos vencidos
    @GetMapping("/overdue")
    public ResponseEntity<List<PlannedExpense>> getOverdue(
            @RequestHeader("X-User-Id") Integer userId) {
        return ResponseEntity.ok(service.getOverdue(userId));
    }

    // GET /planned-expenses/reminders — recordatorios de hoy
    @GetMapping("/reminders")
    public ResponseEntity<List<PlannedExpense>> getReminders(
            @RequestHeader("X-User-Id") Integer userId) {
        return ResponseEntity.ok(service.getTodayReminders(userId));
    }
}