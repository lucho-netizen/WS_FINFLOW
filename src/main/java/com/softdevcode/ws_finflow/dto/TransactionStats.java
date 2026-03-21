package com.softdevcode.ws_finflow.dto;



public class TransactionStats {
    private double totalIncome;
    private double totalExpenses;
    private double balance;
    private double savingsRate;

    public TransactionStats(double totalIncome, double totalExpenses, double balance, double savingsRate) {
        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
        this.balance = balance;
        this.savingsRate = savingsRate;
    }

    public double getTotalIncome() { return totalIncome; }
    public double getTotalExpenses() { return totalExpenses; }
    public double getBalance() { return balance; }
    public double getSavingsRate() { return savingsRate; }
}