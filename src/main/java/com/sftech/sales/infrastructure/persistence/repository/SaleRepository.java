package com.sftech.sales.infrastructure.persistence.repository;

import com.sftech.sales.domain.entity.Sale;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SaleRepository extends JpaRepository<Sale, String> {
    List<Sale> findSalesByUser(String companyId, String userId);

    List<Sale> findByCompanyId(String companyId);

    Optional<Sale> findSaleById(String companyId, String saleId);
}
