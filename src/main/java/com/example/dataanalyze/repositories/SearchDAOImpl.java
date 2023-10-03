package com.example.dataanalyze.repositories;

import com.example.dataanalyze.models.search.input.ExpensesRangeDTO;
import com.example.dataanalyze.models.search.input.ProductDTO;
import com.example.dataanalyze.models.search.output.CustomerDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Репозиторий для выполнения операций по поиску клиентов на основе различных критериев.
 */
@Repository
public class SearchDAOImpl implements SearchDAO {

    private final JdbcTemplate jdbcTemplate;

    public SearchDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CustomerDTO> executeFromLastName(String lastName) {
        return jdbcTemplate.query("select first_name, last_name from sa.customers where last_name = ?", (rs, rowNum) -> {
            CustomerDTO customer = new CustomerDTO();
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            return customer;
        }, lastName);
    }

    @Override
    public List<CustomerDTO> executeFromProductName(ProductDTO product) {
        String sql = "SELECT c.first_name, c.last_name " +
                "FROM sa.customers c " +
                "WHERE c.customer_id IN ( " +
                "    SELECT p.customer_id " +
                "    FROM sa.purchases p " +
                "    JOIN sa.products pr ON p.product_id = pr.product_id " +
                "    WHERE pr.product_name = ? " +
                "    GROUP BY p.customer_id " +
                "    HAVING COUNT(*) >= ?);";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            CustomerDTO customer = new CustomerDTO();
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            return customer;
        }, product.getProductName(), product.getMinTimes());
    }

    @Override
    public List<CustomerDTO> executeFromExpensesRange(ExpensesRangeDTO range) {
        String sql = "SELECT c.first_name, c.last_name " +
                "FROM sa.customers c " +
                "JOIN sa.purchases p ON c.customer_id = p.customer_id " +
                "JOIN sa.products pr ON p.product_id = pr.product_id " +
                "GROUP BY c.first_name, c.last_name " +
                "HAVING SUM(pr.price) BETWEEN ? AND ?;";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            CustomerDTO customer = new CustomerDTO();
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            return customer;
        }, range.getMinExpenses(), range.getMaxExpenses());
    }

    @Override
    public List<CustomerDTO> executeFromBadCustomers(int bad) {
        String sql = "SELECT c.first_name, c.last_name " +
                "FROM sa.customers c " +
                "LEFT JOIN sa.purchases p ON c.customer_id = p.customer_id " +
                "GROUP BY c.first_name, c.last_name " +
                "ORDER BY COUNT(p.purchase_id) ASC " +
                "LIMIT ?;";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            CustomerDTO customer = new CustomerDTO();
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            return customer;
        }, bad);
    }
}
