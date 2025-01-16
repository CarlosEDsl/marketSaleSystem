package com.eduardo.carlos.market.models;

public class SaleItem {

    private Long id;
    private Product product;
    private Integer amount;
    private Double price;

    public SaleItem(Long id, Product product, Integer amount, Double price) {
        this.id = id;
        this.product = product;
        this.amount = amount;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

    public Long getProductId() {
        return this.product.getId();
    }
}

