package com.sftech.sales.infrastructure.persistence.repository;

import com.sftech.sales.domain.entity.Sale;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface SaleRepository extends JpaRepository<Sale, String> {
    @Query("SELECT DISTINCT s FROM Sale s LEFT JOIN FETCH s.items WHERE s.companyId = :companyId AND s.userId = :userId")
    List<Sale> findSalesByUser(@Param("companyId") String companyId, @Param("userId") String userId);

    @Query("SELECT DISTINCT s FROM Sale s LEFT JOIN FETCH s.items WHERE s.companyId = :companyId")
    List<Sale> findByCompanyId(@Param("companyId") String companyId);

    @Query("SELECT DISTINCT s FROM Sale s LEFT JOIN FETCH s.items WHERE s.companyId = :companyId AND s.saleId = :saleId")
    Optional<Sale> findSaleById(@Param("companyId") String companyId, @Param("saleId") String saleId);
}
