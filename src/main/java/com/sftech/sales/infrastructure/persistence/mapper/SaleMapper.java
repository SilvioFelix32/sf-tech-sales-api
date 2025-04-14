package com.sftech.sales.infrastructure.persistence.mapper;

import com.sftech.sales.application.dto.SaleDTO;
import com.sftech.sales.domain.Sale;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SaleMapper {
    SaleMapper INSTANCE = Mappers.getMapper(SaleMapper.class);

    SaleDTO toDTO(Sale sale);

    Sale toEntity(SaleDTO dto);
}