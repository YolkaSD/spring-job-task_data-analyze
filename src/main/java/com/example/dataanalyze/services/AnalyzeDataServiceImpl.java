package com.example.dataanalyze.services;

import com.example.dataanalyze.models.search.input.CriteriasDTO;
import com.example.dataanalyze.models.search.output.SearchDTO;
import com.example.dataanalyze.models.stat.input.StatInput;
import com.example.dataanalyze.models.stat.output.StatDTO;
import com.example.dataanalyze.repositories.StatDAO;
import com.example.dataanalyze.services.parsers.SearchCriteriaDTOCreator;
import com.example.dataanalyze.services.parsers.SearchOutputDTOCreator;
import com.example.dataanalyze.services.parsers.StatCriteriaDTOCreator;
import com.example.dataanalyze.services.parsers.StatOutputDTOCreator;
import com.example.dataanalyze.services.utils.ValidationUtil;
import com.example.dataanalyze.services.write.Writable;
import org.springframework.stereotype.Service;




@Service
public class AnalyzeDataServiceImpl implements AnalyzeDataService {
    private final SearchOutputDTOCreator searchOutputDTOCreator;
    private final SearchCriteriaDTOCreator searchCriteriaDTOCreator;
    private final StatOutputDTOCreator statOutputDTOCreator;
    private final Writable writable;

    public AnalyzeDataServiceImpl(SearchOutputDTOCreator outputForSearchProcessor, SearchCriteriaDTOCreator searchCriteriaDTOCreator, StatDAO statDAO, StatOutputDTOCreator statOutputDTOCreator, Writable writable) {
        this.searchOutputDTOCreator = outputForSearchProcessor;
        this.searchCriteriaDTOCreator = searchCriteriaDTOCreator;
        this.statOutputDTOCreator = statOutputDTOCreator;
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
                handleSearchOperation(inputPath, outputPath, operationType);
                break;
            case "stat":
                handleStatOperation(inputPath, outputPath, operationType);
                break;
        }

    }

    private void handleSearchOperation(String inputPath, String outputPath, String operationType) {
        CriteriasDTO criteriasDTO = searchCriteriaDTOCreator.create(inputPath);
        SearchDTO searchDTO = searchOutputDTOCreator.create(criteriasDTO, operationType);
        writable.write(searchDTO, outputPath);
    }

    private void handleStatOperation(String inputPath, String outputPath, String operationType) {
        StatInput statInput = new StatCriteriaDTOCreator().create(inputPath);
        StatDTO statDTO = statOutputDTOCreator.create(statInput, operationType);
        writable.write(statDTO, outputPath);
    }

}
