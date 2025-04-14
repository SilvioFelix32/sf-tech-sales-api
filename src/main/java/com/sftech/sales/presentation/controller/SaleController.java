package com.sftech.sales.presentation.controller;

import com.sftech.sales.application.dto.SaleDTO;
import com.sftech.sales.application.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
@Tag(name = "Sales", description = "API para gerenciamento de vendas")
public class SaleController {

    private static final Logger logger = LoggerFactory.getLogger(SaleController.class);
    private final SaleService saleService;

    @Operation(summary = "Listar todas as vendas", description = "Retorna uma lista de todas as vendas cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendas encontradas com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public ResponseEntity<List<SaleDTO>> findAll() {
        logger.debug("Iniciando busca de todas as vendas");
        List<SaleDTO> sales = saleService.findAll();
        logger.debug("Vendas encontradas: {}", sales);
        return ResponseEntity.ok(sales);
    }

    @Operation(summary = "Criar uma nova venda", description = "Cadastra uma nova venda no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venda criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    public ResponseEntity<SaleDTO> save(@RequestBody SaleDTO dto) {
        logger.debug("Recebendo requisição para salvar venda: {}", dto);
        SaleDTO savedSale = saleService.save(dto);
        logger.debug("Venda salva com sucesso: {}", savedSale);
        return ResponseEntity.ok(savedSale);
    }
}