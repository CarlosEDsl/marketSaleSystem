package com.eduardo.carlos.market.models.DTOs;

import com.eduardo.carlos.market.models.Product;

public class SaleItemDTO {

    private Long product_id;
    private Integer amount;

    public SaleItemDTO(Long product_id, Integer amount) {
        this.product_id = product_id;
        this.amount = amount;
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
}
