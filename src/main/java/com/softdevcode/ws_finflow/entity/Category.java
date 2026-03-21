package com.softdevcode.ws_finflow.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type; // "Income" o "Expense"

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}