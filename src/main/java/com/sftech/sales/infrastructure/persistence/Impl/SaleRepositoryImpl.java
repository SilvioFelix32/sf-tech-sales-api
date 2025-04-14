package com.sftech.sales.infrastructure.persistence.Impl;

import com.sftech.sales.domain.Sale;
import com.sftech.sales.domain.repository.SaleRepository;
import com.sftech.sales.infrastructure.persistence.repository.SaleJpaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SaleRepositoryImpl implements SaleRepository {

    private final SaleJpaRepository jpaRepository;

    @Override
    public List<Sale> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Sale save(Sale sale) {
        return jpaRepository.save(sale);
    }
}
