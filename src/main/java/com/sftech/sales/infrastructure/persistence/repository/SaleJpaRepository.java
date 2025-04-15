package com.sftech.sales.infrastructure.persistence.repository;

import com.sftech.sales.domain.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleJpaRepository extends JpaRepository<Sale, String> {
}
