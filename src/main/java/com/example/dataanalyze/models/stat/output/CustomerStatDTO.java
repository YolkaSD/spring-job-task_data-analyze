package com.example.dataanalyze.models.stat.output;

import lombok.Data;

import java.util.List;

@Data
public class CustomerStatDTO {
    private String name;
    private List<PurchasesDTO> purchases;
    private double totalExpenses;
}
