package com.example.dataanalyze.services;

import com.example.dataanalyze.models.search.input.CriteriasDTO;
import com.example.dataanalyze.models.search.output.SearchDTO;
import com.example.dataanalyze.services.parsers.SearchCriteriaDTOCreator;
import com.example.dataanalyze.services.parsers.SearchOutputDTOCreator;
import com.example.dataanalyze.services.utils.ValidationUtil;
import com.example.dataanalyze.services.write.Writable;
import org.springframework.stereotype.Service;

@Service
public class AnalyzeDataServiceImpl implements AnalyzeDataService {
    private final SearchOutputDTOCreator searchOutputDTOCreator;
    private final SearchCriteriaDTOCreator searchCriteriaDTOCreator;
    private final Writable writable;

    public AnalyzeDataServiceImpl(SearchOutputDTOCreator outputForSearchProcessor, SearchCriteriaDTOCreator searchCriteriaDTOCreator, Writable writable) {
        this.searchOutputDTOCreator = outputForSearchProcessor;
        this.searchCriteriaDTOCreator = searchCriteriaDTOCreator;
        this.writable = writable;
    }

    @Override
    public void executeOperation(String[] args) {
        ValidationUtil.validateCommandLineArgs(args);

        String operationType = args[0];
        String inputPath = args[1];
        String outputPath = args[2];

        switch (operationType) {
            case "search":
                CriteriasDTO criteriasDTO = searchCriteriaDTOCreator.create(inputPath);
                SearchDTO searchDTO = searchOutputDTOCreator.create(criteriasDTO, operationType);
                writable.write(searchDTO, outputPath);
                break;
            case "stat":
                break;
        }



    }
}
