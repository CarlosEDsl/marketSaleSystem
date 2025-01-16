package com.eduardo.carlos.market.dao;

import com.eduardo.carlos.market.configs.DatabaseConfig;
import com.eduardo.carlos.market.models.Product;
import com.eduardo.carlos.market.models.Sale;
import com.eduardo.carlos.market.models.SaleItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SaleDAO {

    private static SaleDAO instance;
    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<Sale> SALE_ROW_MAPPER = (rs, rowNum) -> {
        return new Sale(
                rs.getLong("id"),
                rs.getDate("date"),
                rs.getDouble("total_amount"),
                null
        );
    };

    public static synchronized SaleDAO getInstance() {
        if (instance == null) {
            instance = new SaleDAO();
        }
        return instance;
    }


    private SaleDAO() {
        this.jdbcTemplate = DatabaseConfig.getJdbcTemplate();
    }

    public List<Sale> getAllSales() {
        String sql = "SELECT * FROM sales";
        return jdbcTemplate.query(sql, SALE_ROW_MAPPER);
    }

    public Sale getSaleById(Long id) {
        String sql = "SELECT * FROM sales WHERE id = ?";
        Sale sale = jdbcTemplate.queryForObject(sql, SALE_ROW_MAPPER, id);

        List<SaleItem> items = getSaleItemsBySaleId(id);
        sale.setItems(items);

        return sale;
    }

    public int createSale(Sale sale) {
        String sql = "INSERT INTO sales (date, total_amount) VALUES (?, ?)";
        int rows = jdbcTemplate.update(sql, sale.getDate(), sale.getTotalAmount());

        Long saleId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        for (SaleItem item : sale.getItems()) {
            createSaleItem(saleId, item);
        }
        return rows;
    }

    public void createSaleItem(Long saleId, SaleItem item) {
        String sql = "INSERT INTO sale_items (sale_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, saleId, item.getProductId(), item.getAmount(), item.getPrice());
    }

    public List<SaleItem> getSaleItemsBySaleId(Long saleId) {
        String sql = "SELECT si.id, si.sale_id, si.product_id, si.amount, si.price, " +
                "p.id AS product_id, p.name AS product_name, p.description AS product_description, " +
                "p.price AS product_price, p.stock_quantity AS product_stock_quantity " +
                "FROM sale_items si " +
                "JOIN products p ON si.product_id = p.id " +
                "WHERE si.sale_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Product product = new Product(
                    rs.getLong("product_id"),
                    rs.getString("product_name"),
                    rs.getString("product_description"),
                    rs.getDouble("product_price"),
                    rs.getInt("product_stock_quantity")
            );

            return new SaleItem(
                    rs.getLong("id"),
                    product,
                    rs.getInt("amount"),
                    rs.getDouble("price")
            );
        }, saleId);
    }

    public int deleteSale(Long id) {
        String deleteItemsSql = "DELETE FROM sale_items WHERE sale_id = ?";
        jdbcTemplate.update(deleteItemsSql, id);

        String deleteSaleSql = "DELETE FROM sales WHERE id = ?";
        return jdbcTemplate.update(deleteSaleSql, id);
    }
}
