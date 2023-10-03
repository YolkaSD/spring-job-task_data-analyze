package com.example.dataanalyze.repositories;


import com.example.dataanalyze.models.stat.input.StatInput;
import com.example.dataanalyze.models.stat.output.CustomerStatDTO;
import com.example.dataanalyze.models.stat.output.PurchasesDTO;

import java.util.List;

public interface StatDAO {
    List<CustomerStatDTO> executeGetNames();

    List<PurchasesDTO> executeGetPurchases(CustomerStatDTO customer, StatInput inputDate);
}
