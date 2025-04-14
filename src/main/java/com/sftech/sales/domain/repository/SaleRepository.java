package com.sftech.sales.domain.repository;

import java.util.List;

import com.sftech.sales.domain.Sale;

public interface SaleRepository {
    List<Sale> findAll();

    Sale save(Sale sale);
}
