package com.example.dataanalyze.services.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Парсит критерии из JSON-файла и возвращает их в виде Map.
 */

public class CriteriaParser {

    /**
     * @param inputPath Путь к JSON-файлу с критериями.
     * @return Map, где ключ - тип критерия, значение - список значений этого типа.
     * @throws IllegalArgumentException Если произошла ошибка при парсинге JSON данных.
     * @throws RuntimeException         Если произошла ошибка при чтении JSON из файла.
     */
    public static Map<String, List<String>> parseCriteriaFromJson(String inputPath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, List<String>> criteriasInput = new HashMap<>();
            JsonNode node = mapper.readTree(new File(inputPath)).get("criterias");
            for (JsonNode criterion : node) {
                String criterionType = getTypeCriterion(criterion);

                if (!criteriasInput.containsKey(criterionType)) {
                    criteriasInput.put(criterionType, new ArrayList<>());
                }

                criteriasInput.get(criterionType).add(criterion.toString());
            }
            return criteriasInput;
        } catch (JsonParseException e) {
            throw new IllegalArgumentException("Ошибка при парсинге JSON данных из файла", e);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении JSON из файла", e);
        }
    }

    private static String getTypeCriterion(JsonNode criteria) {
        if (criteria.has("lastName")) {
            return "lastName";
        } else if (criteria.has("productName") && criteria.has("minTimes")) {
            return "product";
        } else if (criteria.has("minExpenses") && criteria.has("maxExpenses")) {
            return "expensesRange";
        } else if (criteria.has("badCustomers")) {
            return "badCustomers";
        } else {
            return "unknown";
        }
    }
}
