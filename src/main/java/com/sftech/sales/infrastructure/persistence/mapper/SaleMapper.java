package com.sftech.sales.infrastructure.persistence.mapper;

import com.sftech.sales.domain.entity.Sale;
import com.sftech.sales.presentation.dto.SaleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { SaleItemMapper.class })
public interface SaleMapper {
    @Mapping(target = "company_id", source = "companyId")
    @Mapping(target = "user_id", source = "userId")
    SaleDTO toDTO(Sale entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "companyId", source = "company_id")
    @Mapping(target = "userId", source = "user_id")
    Sale toEntity(SaleDTO dto);
}