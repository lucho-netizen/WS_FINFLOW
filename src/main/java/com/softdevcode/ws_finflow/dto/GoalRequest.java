package com.softdevcode.ws_finflow.dto;

import java.time.LocalDate;

public class GoalRequest {
    private String title;
    private Double targetAmount;
    private Double currentAmount;
    private LocalDate deadline;
    private String description;

    // Getters y Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Double getTargetAmount() { return targetAmount; }
    public void setTargetAmount(Double targetAmount) { this.targetAmount = targetAmount; }

    public Double getCurrentAmount() { return currentAmount; }
    public void setCurrentAmount(Double currentAmount) { this.currentAmount = currentAmount; }

    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
