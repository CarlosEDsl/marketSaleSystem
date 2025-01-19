package com.eduardo.carlos.market.controllers;

import com.eduardo.carlos.market.models.DTOs.ObjectDeletedDTO;
import com.eduardo.carlos.market.models.DTOs.ProductDTO;
import com.eduardo.carlos.market.models.DTOs.UpdateProductDTO;
import com.eduardo.carlos.market.models.Product;
import com.eduardo.carlos.market.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll(){
        List<Product> products = this.productService.getAllProducts();
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        ProductDTO product = this.productService.getProductById(id);
        return ResponseEntity.ok().body(product);
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody ProductDTO productDTO) {
        Long id = this.productService.createProduct(productDTO);
        return ResponseEntity.ok(id);
    }

    @PutMapping
    public ResponseEntity<Product> update(@RequestBody UpdateProductDTO productDTO) {
        Product product = this.productService.updateProduct(productDTO);
        return ResponseEntity.ok().body(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ObjectDeletedDTO> delete(@PathVariable Long id) {
        ObjectDeletedDTO deleteObject = this.productService.deleteProduct(id);
        return ResponseEntity.ok().body(deleteObject);
    }


}
