package com.example.dataanalyze.models.search.output.results;

import com.example.dataanalyze.models.search.output.CustomerDTO;
import com.example.dataanalyze.models.search.output.criterias.CriteriaExpensesRangeDTO;
import lombok.Data;

import java.util.List;

@Data
public class ResultExpensesRangeDTO implements ResultDTO {
    private CriteriaExpensesRangeDTO criteria;
    private List<CustomerDTO> result;
}
