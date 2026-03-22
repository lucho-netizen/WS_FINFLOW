package com.softdevcode.ws_finflow.repository;

import com.softdevcode.ws_finflow.entity.PlannedExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlannedExpenseRepository extends JpaRepository<PlannedExpense, Long> {

    List<PlannedExpense> findByUserIdOrderByDueDateAsc(Integer userId);

    Optional<PlannedExpense> findByIdAndUserId(Long id, Integer userId);

    // Gastos que vencen hoy o antes (para alertas)
    List<PlannedExpense> findByUserIdAndStatusAndDueDateLessThanEqual(
            Integer userId, String status, LocalDate date);

    // Gastos a recordar hoy
    List<PlannedExpense> findByUserIdAndRemindAt(Integer userId, LocalDate remindAt);
}