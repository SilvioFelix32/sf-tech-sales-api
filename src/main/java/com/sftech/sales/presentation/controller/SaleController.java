package com.sftech.sales.presentation.controller;

import com.sftech.sales.presentation.dto.CreateSaleDTO;
import com.sftech.sales.presentation.dto.SaleDTO;
import com.sftech.sales.application.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@Tag(name = "Sales", description = "Sales management APIs")
@RequiredArgsConstructor
public class SaleController {

        private final SaleService saleService;
        private final Logger logger = LoggerFactory.getLogger(SaleController.class);

        @PostMapping
        @Operation(summary = "Create a new sale")
        public ResponseEntity<?> createSale(
                        @Parameter(description = "ID da empresa", required = true) @RequestHeader("company_id") @NotBlank(message = "Company ID is required") String companyId,
                        @Parameter(description = "ID do usuário", required = true) @RequestHeader("user_id") @NotBlank(message = "User ID is required") String userId,
                        @Valid @RequestBody CreateSaleDTO createSaleDTO) {
                try {
                        logger.info("Received sale creation request - companyId: {}, userId: {}, body: {}", companyId, userId, createSaleDTO);
                        SaleDTO createdSale = saleService.createSale(companyId, userId, createSaleDTO);
                        return new ResponseEntity<>(createdSale, HttpStatus.CREATED);
                } catch (Exception e) {
                        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
        }

        @GetMapping
        @Operation(summary = "Get all sales")
        public ResponseEntity<?> getAllSales(
                        @Parameter(description = "ID da empresa", required = true) @RequestHeader("company_id") @NotBlank(message = "Company ID is required") String companyId,
                        @Parameter(description = "ID do usuário", required = true) @RequestHeader("user_id") @NotBlank(message = "User ID is required") String userId) {
                try {
                        List<SaleDTO> sales = saleService.getAllSales(companyId, userId);
                        return ResponseEntity.ok(sales);
                } catch (Exception e) {
                        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
        }

        @GetMapping("/{id}")
        @Operation(summary = "Get a sale by ID")
        public ResponseEntity<?> getSaleById(
                        @Parameter(description = "ID da empresa", required = true) @RequestHeader("company_id") @NotBlank(message = "Company ID is required") String companyId,
                        @Parameter(description = "ID do usuário", required = true) @RequestHeader("user_id") @NotBlank(message = "User ID is required") String userId,
                        @PathVariable String id) {
                try {
                        SaleDTO sale = saleService.getSaleById(companyId, userId, id);
                        return ResponseEntity.ok(sale);
                } catch (Exception e) {
                        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
        }
}