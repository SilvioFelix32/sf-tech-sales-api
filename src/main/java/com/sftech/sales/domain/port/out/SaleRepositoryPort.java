package com.sftech.sales.domain.port.out;

import com.sftech.sales.domain.entity.Sale;
import java.util.List;
import java.util.Optional;

public interface SaleRepositoryPort {
    Sale save(Sale sale);
    void delete(Sale sale);
    List<Sale> findSalesByUser(String companyId, String userId);
    List<Sale> findByCompanyId(String companyId);
    Optional<Sale> findSaleById(String companyId, String saleId);
}

