package com.sftech.sales.domain.service;

import com.sftech.sales.application.dto.SaleDTO;
import com.sftech.sales.application.dto.SaleItemDTO;
import com.sftech.sales.domain.entity.Sale;
import com.sftech.sales.domain.entity.SaleItem;
import com.sftech.sales.infrastructure.cache.CacheService;
import com.sftech.sales.infrastructure.exception.BadRequestException;
import com.sftech.sales.infrastructure.exception.SaleNotFoundException;
import com.sftech.sales.infrastructure.persistence.mapper.SaleMapper;
import com.sftech.sales.infrastructure.persistence.repository.SaleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SaleService {
    private static final Logger logger = LoggerFactory.getLogger(SaleService.class);
    private static final String CACHE_KEY_PREFIX = "sales:";
    private static final String CACHE_KEY_COMPANY = CACHE_KEY_PREFIX + "company:";
    private static final String CACHE_KEY_USER = CACHE_KEY_PREFIX + "user:";
    private static final Duration CACHE_TTL = Duration.ofMinutes(30);

    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;
    private final CacheService cacheService;

    public SaleService(SaleRepository saleRepository, SaleMapper saleMapper, CacheService cacheService) {
        this.saleRepository = saleRepository;
        this.saleMapper = saleMapper;
        this.cacheService = cacheService;
    }

    @Transactional
    public SaleDTO createSale(String companyId, String userId, SaleDTO saleDTO) {
        try {
            logger.info("Creating sale for companyId: {}, userId: {}", companyId, userId);
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

            String companyCacheKey = CACHE_KEY_COMPANY + companyId;
            String userCacheKey = CACHE_KEY_USER + companyId + ":" + userId;
            cacheService.delete(companyCacheKey);
            cacheService.delete(userCacheKey);
            logger.info("Cache invalidated for companyId: {} and userId: {}", companyId, userId);

            logger.info("Sale created successfully with id: {}", savedSale.getSaleId());
            return result;
        } catch (Exception e) {
            logger.error("Error creating sale for companyId: {}, userId: {}", companyId, userId, e);
            throw e;
        }
    }

    public List<SaleDTO> getSalesByUser(String companyId, String userId) {
        try {
            logger.info("Getting sales for companyId: {}, userId: {}", companyId, userId);
            validateRequiredFields(companyId, userId);

            String cacheKey = CACHE_KEY_USER + companyId + ":" + userId;
            Optional<Object> cachedValue = cacheService.get(cacheKey);
            
            if (cachedValue.isPresent() && cachedValue.get() instanceof List) {
                @SuppressWarnings("unchecked")
                List<SaleDTO> cachedSales = (List<SaleDTO>) cachedValue.get();
                logger.info("Cache hit for sales by user - companyId: {}, userId: {}", companyId, userId);
                return cachedSales;
            }

            logger.debug("Cache miss for sales by user - companyId: {}, userId: {}", companyId, userId);
            List<Sale> sales = saleRepository.findSalesByUser(companyId, userId);
            List<SaleDTO> result = sales.stream()
                    .map(saleMapper::toDTO)
                    .collect(Collectors.toList());

            cacheService.set(cacheKey, result, CACHE_TTL);
            logger.info("Sales cached for companyId: {}, userId: {}, count: {}", companyId, userId, result.size());
            return result;
        } catch (Exception e) {
            logger.error("Error getting sales by user for companyId: {}, userId: {}", companyId, userId, e);
            throw e;
        }
    }

    public List<SaleDTO> getSalesByCompany(String companyId) {
        try {
            logger.info("Getting sales for companyId: {}", companyId);
            validateRequiredFields(companyId);

            String cacheKey = CACHE_KEY_COMPANY + companyId;
            Optional<Object> cachedValue = cacheService.get(cacheKey);
            
            if (cachedValue.isPresent() && cachedValue.get() instanceof List) {
                @SuppressWarnings("unchecked")
                List<SaleDTO> cachedSales = (List<SaleDTO>) cachedValue.get();
                logger.info("Cache hit for sales by company - companyId: {}", companyId);
                return cachedSales;
            }

            logger.debug("Cache miss for sales by company - companyId: {}", companyId);
            List<Sale> sales = saleRepository.findByCompanyId(companyId);
            List<SaleDTO> result = sales.stream()
                    .map(saleMapper::toDTO)
                    .collect(Collectors.toList());

            cacheService.set(cacheKey, result, CACHE_TTL);
            logger.info("Sales cached for companyId: {}, count: {}", companyId, result.size());
            return result;
        } catch (Exception e) {
            logger.error("Error getting sales by company for companyId: {}", companyId, e);
            throw e;
        }
    }

    public Sale getSaleById(String companyId, String saleId) {
        try {
            logger.info("Getting sale by id - companyId: {}, saleId: {}", companyId, saleId);
            Sale sale = saleRepository.findSaleById(companyId, saleId)
                    .orElseThrow(() -> new SaleNotFoundException(String.format("Sale %s not found", saleId)));
            logger.info("Sale found - companyId: {}, saleId: {}", companyId, saleId);
            return sale;
        } catch (Exception e) {
            logger.error("Error getting sale by id - companyId: {}, saleId: {}", companyId, saleId, e);
            throw e;
        }
    }

    @Transactional
    public void deleteSale(String companyId, String saleId) {
        try {
            logger.info("Deleting sale - companyId: {}, saleId: {}", companyId, saleId);
            Sale sale = getSaleById(companyId, saleId);
            if (sale == null) {
                throw new SaleNotFoundException(String.format("Sale %s not found", saleId));
            }
            saleRepository.delete(sale);

            String companyCacheKey = CACHE_KEY_COMPANY + companyId;
            cacheService.delete(companyCacheKey);
            cacheService.deletePattern(CACHE_KEY_USER + companyId + ":*");
            logger.info("Cache invalidated for companyId: {} after deletion", companyId);

            logger.info("Sale deleted successfully - companyId: {}, saleId: {}", companyId, saleId);
        } catch (Exception e) {
            logger.error("Error deleting sale - companyId: {}, saleId: {}", companyId, saleId, e);
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