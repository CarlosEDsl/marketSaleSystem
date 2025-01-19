package com.eduardo.carlos.market.models;

import java.util.Date;
import java.util.List;

public class Sale {

    private Long id;
    private Date date;
    private Double totalAmount;
    private List<SaleItem> items;

    public Sale(Long id, Date date, Double totalAmount, List<SaleItem> items) {
        this.id = id;
        this.date = date;
        this.totalAmount = totalAmount;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<SaleItem> getItems() {
        return items;
    }

    public void setItems(List<SaleItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", date=" + date +
                ", totalAmount=" + totalAmount +
                ", items=" + items.toString() +
                '}';
    }
}

