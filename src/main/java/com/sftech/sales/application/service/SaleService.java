package com.sftech.sales.application.service;

import com.sftech.sales.application.dto.SaleDTO;
import com.sftech.sales.domain.repository.SaleRepository;
import com.sftech.sales.infrastructure.persistence.mapper.SaleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final SaleMapper mapper = SaleMapper.INSTANCE;

    public List<SaleDTO> findAll() {
        return saleRepository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public SaleDTO save(SaleDTO dto) {
        var sale = mapper.toEntity(dto);
        return mapper.toDTO(saleRepository.save(sale));
    }
}