package com.sftech.sales.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleSearchDTO {
    private String companyId;
    private String userId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double minTotal;
    private Double maxTotal;
}