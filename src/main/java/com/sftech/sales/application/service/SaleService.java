package com.sftech.sales.application.service;

import com.sftech.sales.application.dto.SaleDTO;
import com.sftech.sales.application.dto.SaleItemDTO;
import com.sftech.sales.domain.entity.Sale;
import com.sftech.sales.domain.entity.SaleItem;
import com.sftech.sales.domain.exception.BadRequestException;
import com.sftech.sales.domain.exception.SaleNotFoundException;
import com.sftech.sales.application.port.out.SaleMapperPort;
import com.sftech.sales.domain.port.out.SaleRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SaleService {
    private static final Logger logger = LoggerFactory.getLogger(SaleService.class);

    private final SaleRepositoryPort saleRepository;
    private final SaleMapperPort saleMapper;

    public SaleService(SaleRepositoryPort saleRepository, SaleMapperPort saleMapper) {
        this.saleRepository = saleRepository;
        this.saleMapper = saleMapper;
    }

    @Transactional
    public SaleDTO createSale(String companyId, String userId, SaleDTO saleDTO) {
        logger.info("Creating sale for companyId: {}, userId: {}", companyId, userId);
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
            SaleDTO result = saleMapper.toDTO(savedSale);

            logger.info("Sale created successfully with id: {}", savedSale.getSaleId());
            return result;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error creating sale for companyId: {}, userId: {}", companyId, userId, e);
            throw e;
        }
    }

    public List<SaleDTO> getSalesByUser(String companyId, String userId) {
        logger.info("Getting sales for companyId: {}, userId: {}", companyId, userId);
        try {
            validateRequiredFields(companyId, userId);

            List<Sale> sales = saleRepository.findSalesByUser(companyId, userId);
            List<SaleDTO> result = sales.stream()
                    .map(saleMapper::toDTO)
                    .collect(Collectors.toList());

            return result;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error getting sales by user for companyId: {}, userId: {}", companyId, userId, e);
            throw e;
        }
    }

    public List<SaleDTO> getSalesByCompany(String companyId) {
        logger.info("Getting sales for companyId: {}", companyId);
        try {
            validateRequiredFields(companyId);

            List<Sale> sales = saleRepository.findByCompanyId(companyId);
            List<SaleDTO> result = sales.stream()
                    .map(saleMapper::toDTO)
                    .collect(Collectors.toList());

            return result;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error getting sales by company for companyId: {}", companyId, e);
            throw e;
        }
    }

    public Sale getSaleById(String companyId, String saleId) {
        logger.info("Getting sale by id - companyId: {}, saleId: {}", companyId, saleId);
        try {
            Sale sale = saleRepository.findSaleById(companyId, saleId)
                    .orElseThrow(() -> new SaleNotFoundException(String.format("Sale %s not found", saleId)));
            logger.info("Sale found - companyId: {}, saleId: {}", companyId, saleId);
            return sale;
        } catch (SaleNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error getting sale by id - companyId: {}, saleId: {}", companyId, saleId, e);
            throw e;
        }
    }

    @Transactional
    public void deleteSale(String companyId, String saleId) {
        logger.info("Deleting sale - companyId: {}, saleId: {}", companyId, saleId);
        try {
            Sale sale = getSaleById(companyId, saleId);
            saleRepository.delete(sale);
            logger.info("Sale deleted successfully - companyId: {}, saleId: {}", companyId, saleId);
        } catch (SaleNotFoundException | BadRequestException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error deleting sale - companyId: {}, saleId: {}", companyId, saleId, e);
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

