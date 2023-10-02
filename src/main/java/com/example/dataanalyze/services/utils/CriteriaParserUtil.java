package com.example.dataanalyze.services.utils;

import com.example.dataanalyze.models.creteries.CriteriesDTO;
import com.example.dataanalyze.models.creteries.ExpensesRangeDTO;
import com.example.dataanalyze.models.creteries.ProductDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для чтения и разбора критериев из JSON-файла и создания объекта CriteriesDTO.
 *

 */

public class CriteriaParserUtil {

    public static CriteriesDTO parseFromInput(String inputPath) {
        ObjectMapper mapper = new ObjectMapper();
        List<String> lastNames = new ArrayList<>();
        List<ProductDTO> products = new ArrayList<>();
        List<ExpensesRangeDTO> expensesRanges = new ArrayList<>();
        List<Integer> badCustomers = new ArrayList<>();

        try (FileReader fileReader = new FileReader(inputPath)) {
            JsonNode node = mapper.readTree(fileReader).get("criterias");
            for (JsonNode criterion : node) {
                if (criterion.has("lastName")) {
                    lastNames.add(criterion.get("lastName").asText());
                }
                if (criterion.has("productName") && criterion.has("minTimes")) {
                    ProductDTO product = new ProductDTO();
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

        return CriteriesDTO.builder()
                .lastNames(lastNames)
                .products(products)
                .expensesRanges(expensesRanges)
                .badCustomers(badCustomers)
                .build();
    }
}
