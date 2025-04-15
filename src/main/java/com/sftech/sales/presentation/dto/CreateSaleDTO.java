package com.sftech.sales.presentation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.util.List;

@Data
public class CreateSaleDTO {
    @NotEmpty(message = "A lista de itens não pode estar vazia")
    @Valid
    private List<SaleItemDTO> items;

    @NotNull(message = "O valor total da venda é obrigatório")
    @Positive(message = "O valor total da venda deve ser maior que zero")
    private Double total;
}