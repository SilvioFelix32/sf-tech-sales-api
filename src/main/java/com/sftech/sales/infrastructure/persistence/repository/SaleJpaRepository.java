package com.sftech.sales.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sftech.sales.domain.Sale;

public interface SaleJpaRepository extends JpaRepository<Sale, Long> {
}
