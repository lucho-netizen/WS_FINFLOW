package com.softdevcode.ws_finflow.controller;

import com.softdevcode.ws_finflow.entity.Transaction;
import com.softdevcode.ws_finflow.entity.User;
import com.softdevcode.ws_finflow.repository.TransactionRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final TransactionRepository txRepo;

    public DashboardController(TransactionRepository txRepo) {
        this.txRepo = txRepo;
    }

    @GetMapping
    public Map<String, Object> getDashboard(@RequestHeader("X-User-Id") Integer userId) {
        // Inicio del mes
        LocalDateTime start = LocalDateTime.now().withDayOfMonth(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        LocalDateTime end = LocalDateTime.now();

        // Traer transacciones del usuario desde inicio del mes
        List<Transaction> transactions = txRepo.findByUserIdAndCreatedAtBetweenOrderByCreatedAtDesc(userId, start, end);

        double ingresos = transactions.stream()
                .filter(t -> "income".equals(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double egresos = transactions.stream()
                .filter(t -> "expense".equals(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double saldo = ingresos - egresos;

        Map<String, Map<String, Double>> timeline = new TreeMap<>();
        for (Transaction t : transactions) {
            String day = t.getCreatedAt().toLocalDate().toString();
            timeline.putIfAbsent(day, new HashMap<>(Map.of("income",0.0,"expense",0.0)));
            // Acumular si hay varias transacciones del mismo tipo en un día
            Map<String, Double> dayMap = timeline.get(day);
            dayMap.put(t.getType(), dayMap.get(t.getType()) + t.getAmount());
        }

        return Map.of(
                "ingresos", ingresos,
                "egresos", egresos,
                "saldo", saldo,
                "timeline", timeline,
                "transactions", transactions,
                "total_transactions", transactions.size()
        );
    }
    // Para /overview y /summary se hace de forma similar usando streams y groupBy
}