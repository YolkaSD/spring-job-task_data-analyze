package com.example.dataanalyze.services.parsers;

import com.example.dataanalyze.models.search.input.CriteriasDTO;
import com.example.dataanalyze.models.search.input.ExpensesRangeDTO;
import com.example.dataanalyze.models.search.input.ProductDTO;
import com.example.dataanalyze.models.search.output.SearchDTO;
import com.example.dataanalyze.models.search.output.criterias.CriteriaBadCustomersDTO;
import com.example.dataanalyze.models.search.output.criterias.CriteriaExpensesRangeDTO;
import com.example.dataanalyze.models.search.output.criterias.CriteriaLastNameDTO;
import com.example.dataanalyze.models.search.output.criterias.CriteriaProductDTO;
import com.example.dataanalyze.models.search.output.results.*;
import com.example.dataanalyze.repositories.SearchDAO;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс SearchOutputDTOCreator предназначен для создания объекта SearchDTO
 * на основе данных, полученных из CriteriasDTO и результата выполнения
 * поисковых запросов в базе данных.
 */

@Component
public class SearchOutputDTOCreator {
    private final SearchDAO searchDAO;
    public SearchOutputDTOCreator(SearchDAO searchDAO) {
        this.searchDAO = searchDAO;
    }

    public SearchDTO create(CriteriasDTO criteriasDTO, String operationType) {
        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setType(operationType);
        List<ResultDTO> resultList = new ArrayList<>();
        processLastNames(criteriasDTO, resultList);
        processProducts(criteriasDTO, resultList);
        processExpensesRanges(criteriasDTO, resultList);
        processBadCustomers(criteriasDTO, resultList);
        searchDTO.setResults(resultList);
        return searchDTO;
    }

    private void processLastNames(CriteriasDTO criteriasDTO, List<ResultDTO> resultList) {
        for (String lastName : criteriasDTO.getLastNames()) {
            ResultLastNameDTO result = new ResultLastNameDTO();
            CriteriaLastNameDTO criteria = new CriteriaLastNameDTO();
            criteria.setLastName(lastName);
            result.setCriteria(criteria);
            result.setResult(searchDAO.executeFromLastName(lastName));
            resultList.add(result);
        }
    }

    private void processProducts(CriteriasDTO criteriasDTO, List<ResultDTO> resultList) {
        for (ProductDTO product : criteriasDTO.getProducts()) {
            ResultProductDTO result = new ResultProductDTO();
            CriteriaProductDTO criteria = new CriteriaProductDTO();
            criteria.setMinTimes(product.getMinTimes());
            criteria.setProductName(product.getProductName());
            result.setCriteria(criteria);
            result.setResult(searchDAO.executeFromProductName(product));
            resultList.add(result);
        }
    }

    private void processExpensesRanges(CriteriasDTO criteriasDTO, List<ResultDTO> resultList) {
        for (ExpensesRangeDTO range : criteriasDTO.getExpensesRanges()) {
            ResultExpensesRangeDTO result = new ResultExpensesRangeDTO();
            CriteriaExpensesRangeDTO criteria = new CriteriaExpensesRangeDTO();
            criteria.setMinExpenses(range.getMinExpenses());
            criteria.setMaxExpenses(range.getMaxExpenses());
            result.setCriteria(criteria);
            result.setResult(searchDAO.executeFromExpensesRange(range));
            resultList.add(result);
        }
    }

    private void processBadCustomers(CriteriasDTO criteriasDTO, List<ResultDTO> resultList) {
        for (int bad : criteriasDTO.getBadCustomers()) {
            ResultBadCustomersDTO result = new ResultBadCustomersDTO();
            CriteriaBadCustomersDTO criteria = new CriteriaBadCustomersDTO();
            criteria.setBadCustomers(bad);
            result.setCriteria(criteria);
            result.setBadCustomers(criteria.getBadCustomers());
            result.setResult(searchDAO.executeFromBadCustomers(bad));
            resultList.add(result);
        }
    }
}
