package com.eduardo.carlos.market.models.DTOs;

import com.eduardo.carlos.market.models.SaleItem;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Date;
import java.util.List;

public class SaleDTO {

    private Date date;
    private List<SaleItemDTO> items;

    public SaleDTO(Date date, List<SaleItemDTO> items) {
        this.date = date;
        this.items = items;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<SaleItemDTO> getItems() {
        return items;
    }

    public void setItems(List<SaleItemDTO> items) {
        this.items = items;
    }


}
