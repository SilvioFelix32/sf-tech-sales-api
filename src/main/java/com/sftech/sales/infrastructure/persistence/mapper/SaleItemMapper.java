package com.sftech.sales.infrastructure.persistence.mapper;

import com.sftech.sales.domain.entity.SaleItem;
import com.sftech.sales.presentation.dto.SaleItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleItemMapper {
    @Mapping(target = "category_id", source = "categoryId")
    @Mapping(target = "product_id", source = "productId")
    @Mapping(target = "url_banner", source = "urlBanner")
    @Mapping(target = "total_value", source = "totalValue")
    SaleItemDTO toDTO(SaleItem entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sale", ignore = true)
    @Mapping(target = "categoryId", source = "category_id")
    @Mapping(target = "productId", source = "product_id")
    @Mapping(target = "urlBanner", source = "url_banner")
    @Mapping(target = "totalValue", source = "total_value")
    SaleItem toEntity(SaleItemDTO dto);
}