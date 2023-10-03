package com.example.dataanalyze;

import com.example.dataanalyze.services.AnalyzeDataServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataAnalyzeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataAnalyzeApplication.class, args).getBean("analyzeDataServiceImpl", AnalyzeDataServiceImpl.class).executeOperation(args);

    }


}
