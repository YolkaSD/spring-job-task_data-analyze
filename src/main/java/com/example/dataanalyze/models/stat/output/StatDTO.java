package com.example.dataanalyze.models.stat.output;

import lombok.Data;

import java.util.List;

@Data
public class StatDTO {
    private String type;
    private int totalDays;
    private List<CustomerStatDTO> customers;
    private double totalExpenses;
    private double avgExpenses;
}
