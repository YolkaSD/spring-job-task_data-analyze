package com.example.dataanalyze.models.search.input;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CriteriasDTO {
    private List<String> lastNames;
    private List<ProductSearchDTO> products;
    private List<ExpensesRangeDTO> expensesRanges;
    private List<Integer> badCustomers;
}
