package com.eduardo.carlos.market.models.DTOs;

import com.eduardo.carlos.market.models.Product;

public class SaleItemDTO {

    private Long product_id;
    private Integer amount;
    private Double price;

    public SaleItemDTO(Long product_id, Integer amount, Double price) {
        this.product_id = product_id;
        this.amount = amount;
        this.price = price;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
