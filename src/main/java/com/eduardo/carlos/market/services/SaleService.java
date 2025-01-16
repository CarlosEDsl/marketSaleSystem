package com.eduardo.carlos.market.services;

import com.eduardo.carlos.market.dao.ProductDAO;
import com.eduardo.carlos.market.dao.SaleDAO;
import com.eduardo.carlos.market.models.DTOs.ObjectDeletedDTO;
import com.eduardo.carlos.market.models.DTOs.SaleDTO;
import com.eduardo.carlos.market.models.DTOs.SaleItemDTO;
import com.eduardo.carlos.market.models.Product;
import com.eduardo.carlos.market.models.Sale;
import com.eduardo.carlos.market.models.SaleItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService {

    private final SaleDAO saleDAO;
    private final ProductDAO productDAO;

    public SaleService() {
        this.saleDAO = SaleDAO.getInstance();
        this.productDAO = ProductDAO.getInstance();
    }

    public List<Sale> getAllSales() {
        try{
            return this.saleDAO.getAllSales();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Sale getSale(Long id) {
        try{
            return this.saleDAO.getSaleById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Long createSale(SaleDTO saleDTO) {
        int itemsListSize = saleDTO.getItems().size();

        List<SaleItem> saleItems = new ArrayList<>(itemsListSize);
        for(int i = 0; i < itemsListSize; i++) {
            SaleItemDTO itemDTO = saleDTO.getItems().get(i);
            SaleItem saleItem = this.itemFromDTO(itemDTO);
            this.getAmountItemsOutOfStock(saleItem.getAmount(), saleItem.getProduct());
        }
        Sale sale = new Sale(0L, saleDTO.getDate(), saleDTO.getTotalAmount(), saleItems);

        return (long) this.saleDAO.createSale(sale);
    }

    public SaleItem itemFromDTO(SaleItemDTO saleItemDTO) {
        try{
            Product product = this.productDAO.getProductById(saleItemDTO.getProduct_id());
            if(product == null) {
                throw new RuntimeException("Product not found");
            }
            SaleItem saleItem = new SaleItem(0L, product, saleItemDTO.getAmount(), saleItemDTO.getPrice());
            return saleItem;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void getAmountItemsOutOfStock(int amountOff, Product product) {
        try{
            Integer stockAmount = product.getStockQuantity();
            product.setStockQuantity(stockAmount - amountOff);
            this.productDAO.updateProduct(product);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ObjectDeletedDTO deleteSale(Long id) {
        try{
            Sale sale = this.getSale(id);
            this.productDAO.deleteProduct(id);
            return new ObjectDeletedDTO(sale);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
