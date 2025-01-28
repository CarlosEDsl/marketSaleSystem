package com.eduardo.carlos.market.services;

import com.eduardo.carlos.market.dao.ProductDAO;
import com.eduardo.carlos.market.dao.SaleDAO;
import com.eduardo.carlos.market.exceptions.NotAcceptedValueException;
import com.eduardo.carlos.market.exceptions.NotFoundException;
import com.eduardo.carlos.market.models.DTOs.ObjectDeletedDTO;
import com.eduardo.carlos.market.models.DTOs.SaleDTO;
import com.eduardo.carlos.market.models.DTOs.SaleItemDTO;
import com.eduardo.carlos.market.models.Product;
import com.eduardo.carlos.market.models.Sale;
import com.eduardo.carlos.market.models.SaleItem;
import org.springframework.http.HttpStatus;
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
            throw new NotFoundException(e.getMessage(), HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());
        }
    }

    public Long createSale(SaleDTO saleDTO) {

        int itemsListSize = saleDTO.getItems().size();
        double totalAmount = 0;

        List<SaleItem> saleItems = new ArrayList<SaleItem>(itemsListSize);
        for(int i = 0; i < itemsListSize; i++) {
            if(saleDTO.getItems().get(i).getAmount() < 0){
                throw new NotAcceptedValueException("A quantidade minima por item é 1");
            }
            SaleItemDTO itemDTO = saleDTO.getItems().get(i);
            SaleItem saleItem = this.itemFromDTO(itemDTO);
            this.getAmountItemsOutOfStock(saleItem.getAmount(), saleItem.getProduct());

            saleItems.add(saleItem);
            totalAmount += saleItem.getPrice() * saleItem.getAmount();
        }
        Sale sale = new Sale(0L, saleDTO.getDate(), totalAmount, saleItems);
        try{
            return (long) this.saleDAO.createSale(sale);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public SaleItem itemFromDTO(SaleItemDTO saleItemDTO) {
        try {
            Product product = this.productDAO.getProductById(saleItemDTO.getProduct_id());
            Double productPrice = product.getPrice();

            return new SaleItem(0L, product, saleItemDTO.getAmount(), productPrice);
        }catch (Exception e) {
            throw new NotFoundException("Product not found", HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());
        }
    }

    public void getAmountItemsOutOfStock(int amountOff, Product product) {
        Integer stockAmount = product.getStockQuantity();

        if ((stockAmount - amountOff) < 0) {
            throw new NotAcceptedValueException("Não existem itens suficientes no estoque do item: " + product.getName());
        }

        product.setStockQuantity(stockAmount - amountOff);

        this.productDAO.updateProduct(product);
    }

    public ObjectDeletedDTO deleteSale(Long id) {
        try{
            Sale sale = this.getSale(id);
            this.saleDAO.deleteSale(id);
            return new ObjectDeletedDTO(sale);
        } catch (Exception e) {
            throw new NotFoundException("Erro na deleção, id não encontrado", HttpStatus.NOT_FOUND, 404);
        }
    }
}
