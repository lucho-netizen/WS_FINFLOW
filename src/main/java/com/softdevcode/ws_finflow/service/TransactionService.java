package com.softdevcode.ws_finflow.service;

import com.softdevcode.ws_finflow.dto.MonthlyStats;
import com.softdevcode.ws_finflow.dto.TransactionRequest;
import com.softdevcode.ws_finflow.dto.TransactionStats;
import com.softdevcode.ws_finflow.entity.Transaction;
import com.softdevcode.ws_finflow.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;

    public List<Transaction> getAll(Integer userId) {
        return repository.findByUserIdOrderByDateDesc(userId);
    }

    public List<Transaction> getRecent(Integer userId, int limit) {
        return repository.findByUserIdOrderByDateDesc(userId, PageRequest.of(0, limit));
    }

    @Transactional
    public Transaction create(Integer userId, TransactionRequest request) {
        Transaction tx = new Transaction();
        tx.setUserId(userId);
        tx.setAmount(request.getAmount());
        tx.setType(request.getType());
        tx.setCategoryId(request.getCategoryId());
        tx.setDescription(request.getDescription());
        tx.setDate(request.getDate() != null
                ? request.getDate().atStartOfDay()  // 👈 convierte LocalDate a LocalDateTime
                : LocalDateTime.now());
        tx.setCurrency(request.getCurrency() != null ? request.getCurrency() : "COP");
        tx.setStatus(request.getStatus() != null ? request.getStatus() : "completed");
        tx.setCreatedAt(LocalDateTime.now());
        return repository.save(tx);
    }

    @Transactional
    public void delete(Integer userId, Long id) {
        repository.deleteById(id);
    }

    public TransactionStats getStats(Integer userId) {
        List<Transaction> transactions = repository.findByUserIdOrderByDateDesc(userId);

        double totalIncome = transactions.stream()
                .filter(t -> "income".equals(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double totalExpenses = transactions.stream()
                .filter(t -> "expense".equals(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double balance = totalIncome - totalExpenses;
        double savingsRate = totalIncome > 0 ? ((totalIncome - totalExpenses) / totalIncome) * 100 : 0;

        return new TransactionStats(totalIncome, totalExpenses, balance, savingsRate);
    }

    // Stats Mensuales

    public List<MonthlyStats> getMonthlyStats(Integer userId) {
        List<Transaction> all = repository.findByUserIdOrderByDateDesc(userId);

        Map<String, double[]> map = new LinkedHashMap<>();

        for (Transaction t : all) {
            String key = t.getDate().getYear() + "-" + String.format("%02d", t.getDate().getMonthValue());
            map.putIfAbsent(key, new double[]{0, 0});
            if ("income".equals(t.getType())) map.get(key)[0] += t.getAmount();
            else map.get(key)[1] += t.getAmount();
        }

        return map.entrySet().stream()
                .map(e -> new MonthlyStats(e.getKey(), e.getValue()[0], e.getValue()[1]))
                .collect(Collectors.toList());
    }
}