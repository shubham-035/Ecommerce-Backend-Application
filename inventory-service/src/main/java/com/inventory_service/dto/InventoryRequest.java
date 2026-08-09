package com.inventory_service.dto;

import lombok.Data;

@Data
public class InventoryRequest  {
    private long ProductId;
    private Integer quantity;
}
