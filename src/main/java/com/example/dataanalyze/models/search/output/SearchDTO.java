package com.example.dataanalyze.models.search.output;

import com.example.dataanalyze.models.search.output.results.ResultDTO;
import lombok.Data;

import java.util.List;

@Data
public class SearchDTO {
    private String type;
    private List<ResultDTO> results;
}
