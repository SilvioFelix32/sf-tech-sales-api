package com.sftech.sales.infrastructure.persistence.mapper;

import com.sftech.sales.application.dto.SaleDTO;
import com.sftech.sales.domain.entity.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", uses = { SaleItemMapper.class })
public interface SaleMapper {
    SaleMapper INSTANCE = Mappers.getMapper(SaleMapper.class);

    @Mapping(target = "company_id", source = "companyId")
    @Mapping(target = "user_id", source = "userId") 
    @Mapping(target = "sale_id", source = "saleId")
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "created_at", source ="createdAt")
    @Mapping(target = "updated_at", source ="updatedAt")
    SaleDTO toDTO(Sale entity);

    @Mapping(target = "saleId", ignore = true)
    @Mapping(target = "companyId", source = "company_id")
    @Mapping(target = "userId", source = "user_id")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Sale toEntity(SaleDTO dto);
}