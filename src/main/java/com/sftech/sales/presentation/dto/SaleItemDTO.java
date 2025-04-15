package com.sftech.sales.presentation.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class SaleItemDTO {
    private String category_id;
    private String product_id;
    private String sku;
    private String title;
    private String subtitle;
    private String description;
    private String url_banner;
    private Integer quantity;
    private Double total_value;
}