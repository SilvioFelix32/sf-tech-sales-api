package com.sftech.sales.application.service;

import com.sftech.sales.presentation.dto.CreateSaleDTO;
import com.sftech.sales.presentation.dto.SaleDTO;
import com.sftech.sales.presentation.dto.SaleItemDTO;
import com.sftech.sales.domain.entity.Sale;
import com.sftech.sales.domain.entity.SaleItem;
import com.sftech.sales.domain.repository.SaleRepository;
import com.sftech.sales.infrastructure.persistence.mapper.SaleMapper;
import com.sftech.sales.presentation.exception.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;

    @Transactional
    public SaleDTO createSale(String companyId, String userId, CreateSaleDTO createSaleDTO) {
        try {
            validateRequiredFields(companyId, userId);
            validateItems(createSaleDTO.getItems());

            // TODO: Implementar validação de duplicação de venda
            // 1. Adicionar campo sale_id no CreateSaleDTO para identificar a venda original
            // 2. Validar se já existe uma venda com o mesmo sale_id para a empresa e usuário
            // 3. Se existir, retornar erro de conflito
            // 4. Se não existir, criar a nova venda

            Sale sale = new Sale();
            sale.setCompanyId(companyId);
            sale.setUserId(userId);
            sale.setTotal(createSaleDTO.getTotal());
            sale.setCreatedAt(LocalDateTime.now());
            sale.setUpdatedAt(LocalDateTime.now());

            // Primeiro salva a venda para gerar o ID
            Sale savedSale = saleRepository.save(sale);

            // Cria e adiciona os itens à venda
            createSaleDTO.getItems().forEach(itemDTO -> {
                SaleItem item = new SaleItem();
                item.setCategoryId(itemDTO.getCategory_id());
                item.setProductId(itemDTO.getProduct_id());
                item.setSku(itemDTO.getSku());
                item.setTitle(itemDTO.getTitle());
                item.setSubtitle(itemDTO.getSubtitle());
                item.setDescription(itemDTO.getDescription());
                item.setUrlBanner(itemDTO.getUrl_banner());
                item.setQuantity(itemDTO.getQuantity());
                item.setTotalValue(itemDTO.getTotal_value());
                item.setSale(savedSale);
                savedSale.addItem(item);
            });

            // Salva a venda com os itens
            Sale finalSale = saleRepository.save(savedSale);
            return saleMapper.toDTO(finalSale);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar venda: " + e.getMessage());
        }
    }

    public List<SaleDTO> getAllSales(String companyId, String userId) {
        try {
            validateRequiredFields(companyId, userId);
            List<Sale> sales = saleRepository.findByCompanyIdAndUserId(companyId, userId);
            // Garantir que os itens sejam carregados
            sales.forEach(sale -> sale.getItems().size());
            return sales.stream()
                    .map(saleMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao listar vendas: " + e.getMessage());
        }
    }

    public SaleDTO getSaleById(String companyId, String userId, String id) {
        try {
            validateRequiredFields(companyId, userId);
            Sale sale = saleRepository.findByCompanyIdAndUserIdAndId(companyId, userId, id)
                    .orElseThrow(() -> new NotFoundException("Venda não encontrada para o ID: " + id));
            return saleMapper.toDTO(sale);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao buscar venda: " + e.getMessage());
        }
    }

    private void validateRequiredFields(String companyId, String userId) {
        if (companyId == null || companyId.trim().isEmpty()) {
            throw new BadRequestException("ID da empresa é obrigatório");
        }
        if (userId == null || userId.trim().isEmpty()) {
            throw new BadRequestException("ID do usuário é obrigatório");
        }
    }

    private void validateItems(List<SaleItemDTO> items) {
        if (items == null || items.isEmpty()) {
            throw new BadRequestException("A lista de itens não pode estar vazia");
        }
    }
}