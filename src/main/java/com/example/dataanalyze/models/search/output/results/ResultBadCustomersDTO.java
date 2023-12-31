package com.example.dataanalyze.models.search.output.results;

import com.example.dataanalyze.models.search.output.CustomerSearchDTO;
import com.example.dataanalyze.models.search.output.criterias.CriteriaBadCustomersDTO;
import lombok.Data;

import java.util.List;

@Data
public class ResultBadCustomersDTO implements ResultDTO {
    private CriteriaBadCustomersDTO criteria;
    private int badCustomers;
    List<CustomerSearchDTO> result;
}
