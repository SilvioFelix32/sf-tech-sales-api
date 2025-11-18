package com.sftech.sales.infrastructure.http.controller;

import com.sftech.sales.application.dto.SaleDTO;
import com.sftech.sales.domain.entity.Sale;
import com.sftech.sales.domain.service.SaleService;
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
        SaleDTO sale = saleService.createSale(companyId, userId, saleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(sale);
    }

    @GetMapping
    public ResponseEntity<List<SaleDTO>> getSales(
            @RequestHeader("company_id") String companyId,
            @RequestHeader(value = "user_id", required = false) String userId) {
        List<SaleDTO> sales = (userId != null && !userId.trim().isEmpty())
                ? saleService.getSalesByUser(companyId, userId)
                : saleService.getSalesByCompany(companyId);
        return ResponseEntity.ok(sales);
    }

    @GetMapping("/{saleId}")
    public ResponseEntity<SaleDTO> getSaleById(
            @RequestHeader("company_id") String companyId,
            @PathVariable String saleId) {
        Sale sale = saleService.getSaleById(companyId, saleId);
        return ResponseEntity.ok(saleMapper.toDTO(sale));
    }

    @DeleteMapping("/{saleId}")
    public ResponseEntity<Void> deleteSale(
            @RequestHeader("company_id") String companyId,
            @PathVariable String saleId) {
        saleService.deleteSale(companyId, saleId);
        return ResponseEntity.noContent().build();
    }
}