package com.example.dataanalyze.services.parsers;

import com.example.dataanalyze.models.search.input.CriteriasDTO;
import com.example.dataanalyze.models.search.input.ExpensesRangeDTO;
import com.example.dataanalyze.models.search.input.ProductSearchDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для чтения и разбора критериев из JSON-файла и создания объекта CriteriesDTO.
 */
@Component
public class SearchCriteriaDTOCreator {

    public CriteriasDTO create(String inputPath) {
        ObjectMapper mapper = new ObjectMapper();
        List<String> lastNames = new ArrayList<>();
        List<ProductSearchDTO> products = new ArrayList<>();
        List<ExpensesRangeDTO> expensesRanges = new ArrayList<>();
        List<Integer> badCustomers = new ArrayList<>();

        try (FileReader fileReader = new FileReader(inputPath)) {
            JsonNode node = mapper.readTree(fileReader).get("criterias");
            for (JsonNode criterion : node) {
                if (criterion.has("lastName")) {
                    lastNames.add(criterion.get("lastName").asText());
                }
                if (criterion.has("productName") && criterion.has("minTimes")) {
                    ProductSearchDTO product = new ProductSearchDTO();
                    product.setProductName(criterion.get("productName").asText());
                    product.setMinTimes(criterion.get("minTimes").asInt());
                    products.add(product);
                }

                if (criterion.has("minExpenses") && criterion.has("maxExpenses")) {
                    ExpensesRangeDTO expensesRange = new ExpensesRangeDTO();
                    expensesRange.setMinExpenses(criterion.get("minExpenses").asDouble());
                    expensesRange.setMaxExpenses(criterion.get("maxExpenses").asDouble());
                    expensesRanges.add(expensesRange);
                }

                if (criterion.has("badCustomers")) {
                    badCustomers.add(criterion.get("badCustomers").asInt());
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Ошибка при чтении JSON из файла " + inputPath, e);
        }

        return CriteriasDTO.builder()
                .lastNames(lastNames)
                .products(products)
                .expensesRanges(expensesRanges)
                .badCustomers(badCustomers)
                .build();
    }
}
