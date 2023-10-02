package com.example.dataanalyze.services;

import com.example.dataanalyze.services.utils.CriteriaParser;
import com.example.dataanalyze.services.utils.ValidationUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AnalyzeDataServiceImpl implements AnalyzeDataService {


    @Override
    public void executeOperation(String[] args)  {
        ValidationUtil.validateCommandLineArgs(args);

        String inputPath = args[1];

        Map<String, List<String>> criteriasMap = CriteriaParser.parseCriteriaFromJson(inputPath);

    }
}
