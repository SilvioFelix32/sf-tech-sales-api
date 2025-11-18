package com.sftech.sales.infrastructure.persistence.mapper;

import com.sftech.sales.application.dto.SaleItemDTO;
import com.sftech.sales.domain.entity.SaleItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleItemMapper {
    @Mapping(target = "category_id", source = "categoryId")
    @Mapping(target = "product_id", source = "productId")
    @Mapping(target = "sku", source = "sku")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "subtitle", source = "subtitle")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "url_banner", source = "urlBanner")
    @Mapping(target = "total_value", source = "totalValue")
    @Mapping(target = "quantity", source = "quantity")
    SaleItemDTO toDTO(SaleItem entity);

    @Mapping(target = "categoryId", source = "category_id")
    @Mapping(target = "productId", source = "product_id")
    @Mapping(target = "sku", source = "sku")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "subtitle", source = "subtitle")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "urlBanner", source = "url_banner")
    @Mapping(target = "totalValue", source = "total_value")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "sale", ignore = true)
    @Mapping(target = "saleItemId", ignore = true)
    SaleItem toEntity(SaleItemDTO dto);
}