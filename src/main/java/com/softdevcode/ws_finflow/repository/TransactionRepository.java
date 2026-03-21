package com.softdevcode.ws_finflow.repository;

import com.softdevcode.ws_finflow.entity.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Obtener todas las transacciones de un usuario ordenadas por fecha descendente
    List<Transaction> findByUserIdOrderByDateDesc(Integer userId);

    // Obtener las últimas N transacciones de un usuario usando Pageable
    List<Transaction> findByUserIdOrderByDateDesc(Integer userId, Pageable pageable);

    // Filtrar transacciones por categoría
    List<Transaction> findByUserIdAndCategoryIdOrderByDateDesc(Integer userId, Long categoryId);

    // Filtrar transacciones entre dos fechas
    List<Transaction> findByUserIdAndCreatedAtBetweenOrderByCreatedAtDesc(Integer userId, LocalDateTime start, LocalDateTime end);

    // Filtrar transacciones por estado
    List<Transaction> findByUserIdAndStatusOrderByDateDesc(Integer userId, String status);
}