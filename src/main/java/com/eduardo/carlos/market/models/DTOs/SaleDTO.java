package com.eduardo.carlos.market.models.DTOs;

import com.eduardo.carlos.market.models.SaleItem;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Date;
import java.util.List;

public class SaleDTO {

    private Date date;
    private Double totalAmount;
    private List<SaleItemDTO> items;

    public SaleDTO(Date date, Double totalAmount, List<SaleItemDTO> items) {
        this.date = date;
        this.totalAmount = totalAmount;
        this.items = items;
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

    public List<SaleItemDTO> getItems() {
        return items;
    }

    public void setItems(List<SaleItemDTO> items) {
        this.items = items;
    }


}
