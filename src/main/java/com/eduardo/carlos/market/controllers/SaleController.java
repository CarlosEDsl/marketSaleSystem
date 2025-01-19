package com.eduardo.carlos.market.controllers;

import com.eduardo.carlos.market.models.DTOs.ObjectDeletedDTO;
import com.eduardo.carlos.market.models.DTOs.SaleDTO;
import com.eduardo.carlos.market.models.Sale;
import com.eduardo.carlos.market.services.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sale")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public ResponseEntity<List<Sale>> getAll() {
        List<Sale> sales = this.saleService.getAllSales();
        return ResponseEntity.ok(sales);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> findById(@PathVariable Long id) {
        Sale sale = this.saleService.getSale(id);
        return ResponseEntity.ok(sale);
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody SaleDTO saleDTO) {
        Long id = this.saleService.createSale(saleDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ObjectDeletedDTO> delete(@PathVariable Long id){
        ObjectDeletedDTO objectDeleted = this.saleService.deleteSale(id);
        return ResponseEntity.ok(objectDeleted);
    }

}
