package com.eduardo.carlos.market.dao;

import com.eduardo.carlos.market.configs.DatabaseConfig;
import com.eduardo.carlos.market.models.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class ProductDAO {

    private static ProductDAO instance;
    private final JdbcTemplate jdbcTemplate;

    private static final String CREATE_TABLE_SQL = """
        CREATE TABLE IF NOT EXISTS products (
            id BIGINT AUTO_INCREMENT PRIMARY KEY,
            name VARCHAR(255) NOT NULL,
            description TEXT,
            price DOUBLE NOT NULL,
            stock_quantity INT NOT NULL
        )
    """;

    private static final RowMapper<Product> PRODUCT_ROW_MAPPER = (rs, rowNum) -> new Product(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getDouble("price"),
            rs.getInt("stock_quantity")
    );

    public static synchronized ProductDAO getInstance() {
        if (instance == null) {
            instance = new ProductDAO();
        }
        return instance;
    }

    private ProductDAO() {
        this.jdbcTemplate = DatabaseConfig.getJdbcTemplate();

        this.jdbcTemplate.execute(CREATE_TABLE_SQL);
    }

    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, PRODUCT_ROW_MAPPER);
    }

    public Product getProductById(Long id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, PRODUCT_ROW_MAPPER, id);
    }

    public int createProduct(Product product) {
        String sql = "INSERT INTO products (name, description, price, stock_quantity) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, product.getName(), product.getDescription(), product.getPrice(), product.getStockQuantity());
    }

    public void updateProduct(Product product) {
        String sql = "UPDATE products SET name = ?, description = ?, price = ?, stock_quantity = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getDescription(), product.getPrice(), product.getStockQuantity(), product.getId());
    }

    public int deleteProduct(Long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
