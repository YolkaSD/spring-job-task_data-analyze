package com.example.dataanalyze.repositories;


import com.example.dataanalyze.models.stat.output.CustomerStatDTO;

import java.util.List;

public interface StatDAO {
    List<CustomerStatDTO> executeGetName();
}
