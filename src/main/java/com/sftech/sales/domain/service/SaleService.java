package com.sftech.sales.domain.service;

import com.sftech.sales.application.dto.SaleDTO;
import com.sftech.sales.application.dto.SaleItemDTO;
import com.sftech.sales.domain.entity.Sale;
import com.sftech.sales.domain.entity.SaleItem;
import com.sftech.sales.infrastructure.exception.BadRequestException;
import com.sftech.sales.infrastructure.exception.SaleNotFoundException;
import com.sftech.sales.infrastructure.persistence.mapper.SaleMapper;
import com.sftech.sales.infrastructure.persistence.repository.SaleRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SaleService {
    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;

    public SaleService(SaleRepository saleRepository, SaleMapper saleMapper) {
        this.saleRepository = saleRepository;
        this.saleMapper = saleMapper;
    }

    @Transactional
    public SaleDTO createSale(String companyId, String userId, SaleDTO saleDTO) {
        try {
            validateRequiredFields(companyId, userId);
            validateCreateSaleDTO(saleDTO);

            Sale sale = new Sale();
            sale.setSaleId(UUID.randomUUID().toString());
            sale.setCompanyId(companyId);
            sale.setUserId(userId);
            sale.setTotal(saleDTO.getTotal());
            sale.setCreatedAt(java.time.LocalDateTime.now());
            sale.setUpdatedAt(java.time.LocalDateTime.now());

            List<SaleItem> items = saleDTO.getItems().stream()
                    .map(itemDTO -> {
                        SaleItem item = new SaleItem();
                        item.setSaleItemId(UUID.randomUUID().toString());
                        item.setCategoryId(itemDTO.getCategory_id());
                        item.setProductId(itemDTO.getProduct_id());
                        item.setQuantity(itemDTO.getQuantity());
                        item.setSku(itemDTO.getSku());
                        item.setTitle(itemDTO.getTitle());
                        item.setSubtitle(itemDTO.getSubtitle());
                        item.setDescription(itemDTO.getDescription());
                        item.setUrlBanner(itemDTO.getUrl_banner());
                        item.setTotalValue(itemDTO.getTotal_value());

                        item.setSale(sale);

                        return item;
                    })
                    .collect(Collectors.toList());

            sale.setItems(items);
            Sale savedSale = saleRepository.save(sale);
            return saleMapper.toDTO(savedSale);
        } catch (Exception e) {
            throw e;
        }
    }


    public List<SaleDTO> getSalesByUser(String companyId, String userId) {
        try {
            validateRequiredFields(companyId, userId);
            List<Sale> sales = saleRepository.findSalesByUser(companyId, userId);
            return sales.stream()
                    .map(saleMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw e;
        }
    }

    public List<SaleDTO> getSalesByCompany(String companyId) {
        try {
            validateRequiredFields(companyId);
            List<Sale> sales = saleRepository.findByCompanyId(companyId);
            return sales.stream()
                    .map(saleMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw e;
        }
    }

    public Sale getSaleById(String companyId, String saleId) {
        try {
            return saleRepository.findSaleById(companyId, saleId)
                    .orElseThrow(() -> new SaleNotFoundException("Sale not found"));
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void deleteSale(String companyId, String saleId) {
        try {
            Sale sale = getSaleById(companyId, saleId);
            saleRepository.delete(sale);
        } catch (Exception e) {
            throw e;
        }
    }

    private void validateRequiredFields(String companyId) {
        if (companyId == null || companyId.trim().isEmpty()) {
            throw new BadRequestException("Company ID is required");
        }
    }

    private void validateRequiredFields(String companyId, String userId) {
        validateRequiredFields(companyId);
        if (userId == null || userId.trim().isEmpty()) {
            throw new BadRequestException("User ID is required");
        }
    }

    private void validateCreateSaleDTO(SaleDTO saleDTO) {
        if (saleDTO == null) {
            throw new BadRequestException("Sale data is required");
        }

        if (saleDTO.getItems() == null || saleDTO.getItems().isEmpty()) {
            throw new BadRequestException("Sale items are required");
        }

        for (SaleItemDTO itemDTO : saleDTO.getItems()) {
            if (itemDTO.getCategory_id() == null || itemDTO.getCategory_id().trim().isEmpty()) {
                throw new BadRequestException("Category ID is required for each sale item");
            }
        }
    }
}