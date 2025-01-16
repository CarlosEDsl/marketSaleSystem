package com.eduardo.carlos.market.models.DTOs;

public class UpdateProductDTO {

    private Long id;
    private String description;
    private Double price;
    private Integer stockQuantity;

    public UpdateProductDTO(Long id, String description, Double price, Integer stockQuantity) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }
}
