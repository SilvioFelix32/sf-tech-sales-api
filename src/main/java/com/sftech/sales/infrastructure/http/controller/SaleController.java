package com.sftech.sales.infrastructure.http.controller;

import com.sftech.sales.application.dto.SaleDTO;
import com.sftech.sales.domain.entity.Sale;
import com.sftech.sales.domain.service.SaleService;
import com.sftech.sales.infrastructure.exception.InternalServerErrorException;
import com.sftech.sales.infrastructure.persistence.mapper.SaleMapper;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/sales")
public class SaleController {
    private final SaleService saleService;
    private final SaleMapper saleMapper;

    public SaleController(SaleService saleService, SaleMapper saleMapper) {
        this.saleService = saleService;
        this.saleMapper = saleMapper;
    }

    @PostMapping
    public ResponseEntity<SaleDTO> createSale(
            @RequestHeader("company_id") String companyId,
            @RequestHeader("user_id") String userId,
            @RequestBody SaleDTO saleDTO) {
        try {
            SaleDTO sale = saleService.createSale(companyId, userId, saleDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(sale);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error creating sale: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<SaleDTO>> getSales(
            @RequestHeader("company_id") String companyId,
            @RequestHeader(value = "user_id", required = false) String userId) {
        try {
            List<SaleDTO> sales;
            if (userId != null && !userId.trim().isEmpty()) {
                sales = saleService.getSalesByUser(companyId, userId);
            } else {
                sales = saleService.getSalesByCompany(companyId);
            }
            return ResponseEntity.ok(sales);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error getting sales: " + e.getMessage());
        }
    }

    @GetMapping("/{saleId}")
    public ResponseEntity<SaleDTO> getSaleById(
            @RequestHeader("company_id") String companyId,
            @PathVariable String saleId) {
        try {
            Sale sale = saleService.getSaleById(companyId, saleId);
            return ResponseEntity.ok(saleMapper.toDTO(sale));
        } catch (Exception e) {
            throw new InternalServerErrorException("Error getting sale: " + e.getMessage());
        }
    }

    @DeleteMapping("/{saleId}")
    public ResponseEntity<Void> deleteSale(
            @RequestHeader("company_id") String companyId,
            @PathVariable String saleId) {
        try {
            saleService.deleteSale(companyId, saleId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Error deleting sale: " + e.getMessage());
        }
    }
}