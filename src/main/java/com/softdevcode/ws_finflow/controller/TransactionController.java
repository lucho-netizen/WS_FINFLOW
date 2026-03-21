package com.softdevcode.ws_finflow.controller;

import com.softdevcode.ws_finflow.dto.MonthlyStats;
import com.softdevcode.ws_finflow.dto.TransactionRequest;
import com.softdevcode.ws_finflow.dto.TransactionStats;
import com.softdevcode.ws_finflow.entity.Transaction;
import com.softdevcode.ws_finflow.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    @Autowired
    private TransactionService service;

    // Últimas 5 transacciones
    @GetMapping("/recent")
    public ResponseEntity<List<Transaction>> getAll(@RequestHeader("X-User-Id") Integer userId) {
        return ResponseEntity.ok(service.getAll(userId));
    }

    // Crear transacción
    @PostMapping
    public ResponseEntity<Transaction> create(@RequestHeader("X-User-Id") Integer userId,
                                              @RequestBody TransactionRequest request) {
        Transaction tx = service.create(userId, request);
        return ResponseEntity.ok(tx);
    }

    // Eliminar transacción
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@RequestHeader("X-User-Id") Integer userId,
                                       @PathVariable Long id) {
        service.delete(userId, id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/stats")
    public ResponseEntity<TransactionStats> getStats(@RequestHeader("X-User-Id") Integer userId) {
        return ResponseEntity.ok(service.getStats(userId));
    }

    @GetMapping("/monthly-stats")
    public ResponseEntity<List<MonthlyStats>> getMonthlyStats(@RequestHeader("X-User-Id") Integer userId) {
        return ResponseEntity.ok(service.getMonthlyStats(userId));
    }
}