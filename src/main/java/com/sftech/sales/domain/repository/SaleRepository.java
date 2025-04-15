package com.sftech.sales.domain.repository;

import com.sftech.sales.domain.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, String> {
    List<Sale> findByCompanyIdAndUserId(String companyId, String userId);

    Optional<Sale> findByCompanyIdAndUserIdAndId(String companyId, String userId, String id);
}
