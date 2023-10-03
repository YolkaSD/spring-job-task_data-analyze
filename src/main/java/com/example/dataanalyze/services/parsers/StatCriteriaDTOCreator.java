package com.example.dataanalyze.services.parsers;

import com.example.dataanalyze.models.stat.input.StatInput;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;

@Component
public class StatCriteriaDTOCreator {

    public StatInput create(String inputPath) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.readValue(new File(inputPath), new TypeReference<StatInput>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
