package com.example.dataanalyze.models.creteries;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CriteriesDTO {
    private List<String> lastNames;
    private List<ProductDTO> products;
    private List<ExpensesRangeDTO> expensesRanges;
    private List<Integer> badCustomers;
}
