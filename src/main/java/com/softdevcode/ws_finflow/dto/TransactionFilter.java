package com.softdevcode.ws_finflow.dto;

import lombok.Getter;

@Getter
public class TransactionFilter {
    private String type;        // income | expense | null = todos
    private Integer month;      // 1-12 | null = todos
    private Integer year;       // ej: 2026 | null = todos
    private Long categoryId;    // null = todas
    private String search;      // búsqueda por descripción

    public void setType(String type) { this.type = type; }

    public void setMonth(Integer month) { this.month = month; }

    public void setYear(Integer year) { this.year = year; }

    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public void setSearch(String search) { this.search = search; }
}