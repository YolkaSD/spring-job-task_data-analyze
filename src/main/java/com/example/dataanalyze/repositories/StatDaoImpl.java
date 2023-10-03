package com.example.dataanalyze.repositories;

import com.example.dataanalyze.models.stat.output.CustomerStatDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatDaoImpl implements StatDAO{

    private final JdbcTemplate jdbcTemplate;

    public StatDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CustomerStatDTO> executeGetName() {
        return jdbcTemplate.query("select first_name, last_name from sa.customers", (rs, rowNum) -> {
            CustomerStatDTO customerStatDTO = new CustomerStatDTO();
            customerStatDTO.setName(rs.getString("first_name") + " " + rs.getString("last_name"));
            return customerStatDTO;
        });
    }
}
