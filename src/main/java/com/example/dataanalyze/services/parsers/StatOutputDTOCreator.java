package com.example.dataanalyze.services.parsers;
import com.example.dataanalyze.models.stat.input.StatInput;
import com.example.dataanalyze.models.stat.output.CustomerStatDTO;
import com.example.dataanalyze.models.stat.output.PurchasesDTO;
import com.example.dataanalyze.models.stat.output.StatDTO;
import com.example.dataanalyze.repositories.StatDAO;
import com.example.dataanalyze.services.utils.WorkingDaysCalculator;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Создает объект StatDTO на основе входных данных.
 *
 */

@Component
public class StatOutputDTOCreator {

    private final StatDAO statDAO;
    private static final String DECIMAL_FORMAT_PATTERN = "#.#";

    public StatOutputDTOCreator(StatDAO statDAO) {
        this.statDAO = statDAO;
    }

    public StatDTO create(StatInput statInput, String operationType) {
        StatDTO statDTO = new StatDTO();
        statDTO.setType(operationType);

        int totalDays = WorkingDaysCalculator.calculateWorkingDays(statInput.getStartDate(), statInput.getEndDate());
        statDTO.setTotalDays(totalDays);

        List<CustomerStatDTO> customerStatDTOList = statDAO.executeGetNames();
        statDTO.setCustomers(customerStatDTOList);

        double totalExpenses = calculateTotalExpenses(customerStatDTOList, statInput);
        statDTO.setTotalExpenses(totalExpenses);

        double avgExpenses = calculateAverageExpenses(totalExpenses, customerStatDTOList.size());
        statDTO.setAvgExpenses(avgExpenses);

        return statDTO;
    }

    private double calculateTotalExpenses(List<CustomerStatDTO> customers, StatInput statInput) {
        double totalExpenses = 0;

        for (CustomerStatDTO customer : customers) {
            List<PurchasesDTO> purchases = statDAO.executeGetPurchases(customer, statInput);
            double expenses = purchases.stream().mapToDouble(PurchasesDTO::getExpenses).sum();
            totalExpenses += expenses;
            customer.setTotalExpenses(formatDouble(expenses));
            customer.setPurchases(purchases);
        }

        return totalExpenses;
    }

    private double calculateAverageExpenses(double totalExpenses, int customerCount) {
        if (customerCount == 0) {
            return 0;
        }
        return formatDouble(totalExpenses / customerCount);
    }

    private double formatDouble(double value) {
        return Double.parseDouble(new DecimalFormat(DECIMAL_FORMAT_PATTERN).format(value).replace(",", "."));
    }
}
