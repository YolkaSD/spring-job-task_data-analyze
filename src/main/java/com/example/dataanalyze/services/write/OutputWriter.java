package com.example.dataanalyze.services.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Класс SearchWriter предоставляет функциональность для записи объектов в файл в формате JSON.
 *
 */
@Component
public class OutputWriter implements Writable {


    @Override
    public void write(Object dto, String outputPath) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try (FileWriter fileWriter = new FileWriter(outputPath)) {
            mapper.writeValue(fileWriter, dto);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при записи объекта в файл", e);
        }
    }
}
