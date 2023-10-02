package com.example.dataanalyze.services;
import com.example.dataanalyze.services.utils.CriteriaParserUtil;
import com.example.dataanalyze.services.utils.ValidationUtil;
import org.springframework.stereotype.Service;

@Service
public class AnalyzeDataServiceImpl implements AnalyzeDataService {


    @Override
    public void executeOperation(String[] args)  {
        ValidationUtil.validateCommandLineArgs(args);

        String inputPath = args[1];

        System.out.println(CriteriaParserUtil.createInput(inputPath));

    }
}
