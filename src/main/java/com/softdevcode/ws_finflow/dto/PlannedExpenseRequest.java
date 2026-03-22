package com.softdevcode.ws_finflow.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PlannedExpenseRequest {

    private String     title;
    private BigDecimal amount;
    private Long       categoryId;
    private LocalDate  dueDate;
    private String     notes;
    private LocalDate  remindAt;

    public String     getTitle()      { return title; }
    public void       setTitle(String title) { this.title = title; }

    public BigDecimal getAmount()     { return amount; }
    public void       setAmount(BigDecimal amount) { this.amount = amount; }

    public Long       getCategoryId() { return categoryId; }
    public void       setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public LocalDate  getDueDate()    { return dueDate; }
    public void       setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public String     getNotes()      { return notes; }
    public void       setNotes(String notes) { this.notes = notes; }

    public LocalDate  getRemindAt()   { return remindAt; }
    public void       setRemindAt(LocalDate remindAt) { this.remindAt = remindAt; }
}