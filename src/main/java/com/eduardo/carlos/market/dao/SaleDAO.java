package com.eduardo.carlos.market.dao;

import com.eduardo.carlos.market.configs.DatabaseConfig;
import com.eduardo.carlos.market.models.Product;
import com.eduardo.carlos.market.models.Sale;
import com.eduardo.carlos.market.models.SaleItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class SaleDAO {

    private static SaleDAO instance;
    private final JdbcTemplate jdbcTemplate;

    private static final String CREATE_SALE_TABLE_SQL = """
        CREATE TABLE IF NOT EXISTS sale (
            id BIGINT AUTO_INCREMENT PRIMARY KEY,
            date DATE NOT NULL,
            totalAmount DOUBLE NOT NULL
        )
        """;

    private static final String CREATE_SALE_ITEMS_TABLE_SQL = """
        CREATE TABLE IF NOT EXISTS sale_items (
            id BIGINT AUTO_INCREMENT PRIMARY KEY,
            sale_id BIGINT NOT NULL,
            product_id BIGINT NOT NULL,
            amount INT NOT NULL,
            price DOUBLE NOT NULL,
            FOREIGN KEY (sale_id) REFERENCES sale (id) ON DELETE CASCADE,
            FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
        )
        """;

    private static final RowMapper<Sale> SALE_ROW_MAPPER = (rs, rowNum) -> new Sale(
            rs.getLong("id"),
            rs.getDate("date"),
            rs.getDouble("totalAmount"),
            null
    );

    public static synchronized SaleDAO getInstance() {
        if (instance == null) {
            instance = new SaleDAO();
        }
        return instance;
    }

    private SaleDAO() {
        this.jdbcTemplate = DatabaseConfig.getJdbcTemplate();

        this.jdbcTemplate.execute(CREATE_SALE_TABLE_SQL);
        this.jdbcTemplate.execute(CREATE_SALE_ITEMS_TABLE_SQL);
    }

    public List<Sale> getAllSales() {
        String sql = "SELECT * FROM sale";
        List<Sale> sales = jdbcTemplate.query(sql, SALE_ROW_MAPPER);

        for(Sale sale : sales){
            sale.setItems(this.getSaleItemsBySaleId(sale.getId()));
        }

        return sales;
    }

    public Sale getSaleById(Long id) {
        String sql = "SELECT * FROM sale WHERE id = ?";
        Sale sale = jdbcTemplate.queryForObject(sql, SALE_ROW_MAPPER, id);

        List<SaleItem> items = getSaleItemsBySaleId(id);
        sale.setItems(items);

        return sale;
    }

    @Transactional
    public long createSale(Sale sale) {
        String sql = "INSERT INTO sale (date, totalAmount) VALUES (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, new java.sql.Date(sale.getDate().getTime())); // Converte java.util.Date para SQL Date
            ps.setDouble(2, sale.getTotalAmount());
            return ps;
        }, keyHolder);

        Long saleId = keyHolder.getKey().longValue();

        for (SaleItem item : sale.getItems()) {
            createSaleItem(saleId, item);
        }

        return saleId;
    }

    @Transactional
    public void createSaleItem(Long saleId, SaleItem item) {
        System.out.println(saleId);
        System.out.println(item.getProductId());
        String sql = "INSERT INTO sale_items (sale_id, product_id, amount, price) VALUES (?, ?, ?, ?)";
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

        String deleteSaleSql = "DELETE FROM sale WHERE id = ?";
        return jdbcTemplate.update(deleteSaleSql, id);
    }
}
