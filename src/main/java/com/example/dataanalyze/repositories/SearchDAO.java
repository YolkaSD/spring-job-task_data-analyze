package com.example.dataanalyze.repositories;

import com.example.dataanalyze.models.search.input.ExpensesRangeDTO;
import com.example.dataanalyze.models.search.input.ProductSearchDTO;
import com.example.dataanalyze.models.search.output.CustomerSearchDTO;

import java.util.List;

public interface SearchDAO {
    List<CustomerSearchDTO> executeFromLastName(String lastName);

    List<CustomerSearchDTO> executeFromProductName(ProductSearchDTO product);

    List<CustomerSearchDTO> executeFromExpensesRange(ExpensesRangeDTO range);

    List<CustomerSearchDTO> executeFromBadCustomers(int bad);
}
