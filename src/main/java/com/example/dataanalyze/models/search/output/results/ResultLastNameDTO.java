package com.example.dataanalyze.models.search.output.results;

import com.example.dataanalyze.models.search.output.CustomerDTO;
import com.example.dataanalyze.models.search.output.criterias.CriteriaLastNameDTO;
import lombok.Data;

import java.util.List;

@Data
public class ResultLastNameDTO implements ResultDTO {
    private CriteriaLastNameDTO criteria;
    List<CustomerDTO> result;
}
