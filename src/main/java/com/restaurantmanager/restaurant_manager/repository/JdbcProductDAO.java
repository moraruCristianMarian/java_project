package com.restaurantmanager.restaurant_manager.repository;

import com.restaurantmanager.restaurant_manager.json.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcProductDAO {
    private JdbcTemplate jdbcTemplate;

    public Product getByCode(String code) {
        Object[] args = {code};
        return this.jdbcTemplate.queryForObject(
                "select id, product_category_id, name, cost from products where code = ?",
                args, new RowMapper<Product>() {

            public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Product(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getDouble(4)
                );
            }
        });
    }
}