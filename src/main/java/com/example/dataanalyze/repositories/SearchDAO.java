package com.example.dataanalyze.repositories;

import com.example.dataanalyze.models.search.input.ExpensesRangeDTO;
import com.example.dataanalyze.models.search.input.ProductDTO;
import com.example.dataanalyze.models.search.output.CustomerDTO;

import java.util.List;

public interface SearchDAO {
    List<CustomerDTO> executeFromLastName(String lastName);

    List<CustomerDTO> executeFromProductName(ProductDTO product);

    List<CustomerDTO> executeFromExpensesRange(ExpensesRangeDTO range);

    List<CustomerDTO> executeFromBadCustomers(int bad);
}
