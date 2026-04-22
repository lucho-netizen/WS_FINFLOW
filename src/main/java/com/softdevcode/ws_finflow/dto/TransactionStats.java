package com.softdevcode.ws_finflow.dto;

import java.util.List;
import java.util.Map;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class TransactionStats {
    private double totalIncome;
    private double totalExpenses;
    private double balance;
    private double savingsRate;
    private List<Map<String, Object>> monthlyData;
    private List<Map<String, Object>> categoryData;

    public TransactionStats(double totalIncome, double totalExpenses,
                            double balance, double savingsRate,
                            List<Map<String, Object>> monthlyData,
                            List<Map<String, Object>> categoryData) {
        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
        this.balance = balance;
        this.savingsRate = savingsRate;
        this.monthlyData = monthlyData;
        this.categoryData = categoryData;
    }

    public double getTotalIncome() { return totalIncome; }
    public double getTotalExpenses() { return totalExpenses; }
    public double getBalance() { return balance; }
    public double getSavingsRate() { return savingsRate; }
    public List<Map<String, Object>> getMonthlyData() { return monthlyData; }
    public List<Map<String, Object>> getCategoryData() { return categoryData; }
}