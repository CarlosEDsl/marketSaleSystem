package com.eduardo.carlos.market.services;

import com.eduardo.carlos.market.dao.ProductDAO;
import com.eduardo.carlos.market.exceptions.NotFoundException;
import com.eduardo.carlos.market.models.DTOs.ObjectDeletedDTO;
import com.eduardo.carlos.market.models.DTOs.ProductDTO;
import com.eduardo.carlos.market.models.DTOs.UpdateProductDTO;
import com.eduardo.carlos.market.models.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductDAO productDAO;

    public ProductService() {
        this.productDAO = ProductDAO.getInstance();
    }

    public List<Product> getAllProducts() {
        try{
            return this.productDAO.getAllProducts();
        } catch(Exception e) {
            throw new NotFoundException(e.getMessage(), HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());
        }
    }

    public ProductDTO getProductById(Long id) {
        try{
            Product product =  this.productDAO.getProductById(id);
            return new ProductDTO(product.getName(), product.getDescription(),product.getPrice(),
                    product.getStockQuantity());
        } catch(Exception e) {
            throw new NotFoundException(e.getMessage(), HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());
        }
    }

    public long createProduct(ProductDTO productDTO) {
        try{
            Product product = new Product(0L, productDTO.getName(), productDTO.getDescription(),
                    productDTO.getPrice(), productDTO.getStockQuantity());

            int id = productDAO.createProduct(product);
            return id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Product updateProduct(UpdateProductDTO productDTO) {
        try{
            Product product = this.productDAO.getProductById(productDTO.getId());

            product.setDescription(productDTO.getDescription());
            product.setPrice(productDTO.getPrice());
            product.setStockQuantity(productDTO.getStockQuantity());

            this.productDAO.updateProduct(product);

            return product;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ObjectDeletedDTO deleteProduct(Long id) {
        try{
            ProductDTO product = this.getProductById(id);
            this.productDAO.deleteProduct(id);

            return new ObjectDeletedDTO(product);
        } catch(Exception e) {
            throw new NotFoundException(e.getMessage(), HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());
        }

    }

}
