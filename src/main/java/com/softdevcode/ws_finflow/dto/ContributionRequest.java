package com.softdevcode.ws_finflow.dto;

public class ContributionRequest {

    private Double amount;
    private String note; // opcional

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}