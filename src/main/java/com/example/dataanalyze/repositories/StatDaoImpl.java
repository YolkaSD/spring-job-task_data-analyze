package com.example.dataanalyze.repositories;

import com.example.dataanalyze.models.stat.input.StatInput;
import com.example.dataanalyze.models.stat.output.CustomerStatDTO;
import com.example.dataanalyze.models.stat.output.PurchasesDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StatDaoImpl implements StatDAO{

    private final JdbcTemplate jdbcTemplate;

    public StatDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CustomerStatDTO> executeGetNames() {
        return jdbcTemplate.query("select first_name, last_name from sa.customers", (rs, rowNum) -> {
            CustomerStatDTO customerStatDTO = new CustomerStatDTO();
            customerStatDTO.setName(rs.getString("first_name") + " " + rs.getString("last_name"));
            return customerStatDTO;
        });
    }

    @Override
    public List<PurchasesDTO> executeGetPurchases(CustomerStatDTO customer, StatInput inputDate) {
        String sql = "SELECT DISTINCT " +
                "    pr.product_name AS name, " +
                "    SUM(pr.price) AS expenses " +
                "FROM " +
                "    sa.customers c " +
                "    JOIN sa.purchases pu ON c.customer_id = pu.customer_id " +
                "    JOIN sa.products pr ON pu.product_id = pr.product_id " +
                "WHERE " +
                "    c.first_name = ? AND c.last_name = ? " +
                "    AND pu.purchase_date BETWEEN ? AND ? " +
                "GROUP BY " +
                "    pr.product_name " +
                "ORDER BY " +
                "    SUM(pr.price); ";


        String fullName = customer.getName();
        String[] nameParts = fullName.split(" ");

        Object[] args = {nameParts[0], nameParts[1], inputDate.getStartDate(), inputDate.getEndDate()};

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            PurchasesDTO purchasesDTO = new PurchasesDTO();
            purchasesDTO.setName(rs.getString("name"));
            purchasesDTO.setExpenses(rs.getDouble("expenses"));
            return purchasesDTO;
        }, args);

    }
}
