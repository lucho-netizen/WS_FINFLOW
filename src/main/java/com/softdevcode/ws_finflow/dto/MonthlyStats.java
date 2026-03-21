package com.softdevcode.ws_finflow.dto;

public class MonthlyStats {
    private String month;
    private double income;
    private double expenses;

    public MonthlyStats(String month, double income, double expenses) {
        this.month = month;
        this.income = income;
        this.expenses = expenses;
    }

    public String getMonth() { return month; }
    public double getIncome() { return income; }
    public double getExpenses() { return expenses; }
}