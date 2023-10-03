package com.example.dataanalyze.models.search.output.results;
import com.example.dataanalyze.models.search.output.CustomerSearchDTO;
import com.example.dataanalyze.models.search.output.criterias.CriteriaProductDTO;
import lombok.Data;

import java.util.List;

@Data
public class ResultProductDTO implements ResultDTO {
    private CriteriaProductDTO criteria;
    private List<CustomerSearchDTO> result;
}
