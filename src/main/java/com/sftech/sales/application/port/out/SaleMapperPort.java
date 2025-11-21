package com.sftech.sales.application.port.out;

import com.sftech.sales.application.dto.SaleDTO;
import com.sftech.sales.domain.entity.Sale;

public interface SaleMapperPort {
    SaleDTO toDTO(Sale entity);
    Sale toEntity(SaleDTO dto);
}

