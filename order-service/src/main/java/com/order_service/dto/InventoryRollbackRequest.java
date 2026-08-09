package com.order_service.dto;

import lombok.Data;

@Data
public class InventoryRollbackRequest {

    private Long productId;
    private Integer quantity;
}
