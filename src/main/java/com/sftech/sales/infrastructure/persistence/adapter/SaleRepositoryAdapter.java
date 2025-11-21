package com.sftech.sales.infrastructure.persistence.adapter;

import com.sftech.sales.domain.entity.Sale;
import com.sftech.sales.domain.port.out.SaleRepositoryPort;
import com.sftech.sales.infrastructure.persistence.repository.SaleRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SaleRepositoryAdapter implements SaleRepositoryPort {
    private final SaleRepository saleRepository;

    public SaleRepositoryAdapter(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @SuppressWarnings("null")
    @Override
    public Sale save(Sale sale) {
        return saleRepository.save(sale);
    }

    @SuppressWarnings("null")
    @Override
    public void delete(Sale sale) {
        saleRepository.delete(sale);
    }

    @Override
    public List<Sale> findSalesByUser(String companyId, String userId) {
        return saleRepository.findSalesByUser(companyId, userId);
    }

    @Override
    public List<Sale> findByCompanyId(String companyId) {
        return saleRepository.findByCompanyId(companyId);
    }

    @Override
    public Optional<Sale> findSaleById(String companyId, String saleId) {
        return saleRepository.findSaleById(companyId, saleId);
    }
}

