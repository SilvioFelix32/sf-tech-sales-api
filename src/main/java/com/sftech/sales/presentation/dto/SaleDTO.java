package com.sftech.sales.presentation.dto;

import lombok.Data;
import lombok.Builder;
import java.util.List;

@Data
@Builder
public class SaleDTO {
    private String company_id;
    private String user_id;
    private Double total;
    private List<SaleItemDTO> items;
}