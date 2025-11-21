package com.sftech.sales.infrastructure.persistence.adapter;

import com.sftech.sales.application.dto.SaleDTO;
import com.sftech.sales.application.port.out.SaleMapperPort;
import com.sftech.sales.domain.entity.Sale;
import com.sftech.sales.infrastructure.persistence.mapper.SaleMapper;
import org.springframework.stereotype.Component;

@Component
public class SaleMapperAdapter implements SaleMapperPort {
    private final SaleMapper saleMapper;

    public SaleMapperAdapter(SaleMapper saleMapper) {
        this.saleMapper = saleMapper;
    }

    @Override
    public SaleDTO toDTO(Sale entity) {
        return saleMapper.toDTO(entity);
    }

    @Override
    public Sale toEntity(SaleDTO dto) {
        return saleMapper.toEntity(dto);
    }
}

