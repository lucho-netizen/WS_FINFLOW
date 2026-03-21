package com.softdevcode.ws_finflow.dto;

import java.time.LocalDate;

public class GoalResponse {
    private Long id;
    private String title;
    private Double targetAmount;
    private Double currentAmount;
    private LocalDate deadline;
    private String description;

    public GoalResponse(Long id, String title, Double targetAmount, Double currentAmount, LocalDate deadline, String description) {
        this.id = id;
        this.title = title;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.deadline = deadline;
        this.description = description;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public Double getTargetAmount() { return targetAmount; }
    public Double getCurrentAmount() { return currentAmount; }
    public LocalDate getDeadline() { return deadline; }
    public String getDescription() { return description; }
}