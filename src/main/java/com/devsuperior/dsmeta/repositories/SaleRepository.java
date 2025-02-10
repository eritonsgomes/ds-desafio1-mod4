package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SaleSummaryMinProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT s FROM Sale s JOIN FETCH s.seller WHERE s.id = :id")
    Sale searchById(Long id);

    @Query("SELECT s FROM Sale s JOIN FETCH s.seller " +
            "WHERE UPPER(s.seller.name) LIKE UPPER(CONCAT('%', :name, '%')) " +
            "AND s.date BETWEEN :initialDate AND :finalDate ORDER BY s.date DESC, s.seller.name")
    List<Sale> searchReportBySellerNameAndDateBetween(String name, LocalDate initialDate, LocalDate finalDate);

    @Query(value = "SELECT s FROM Sale s JOIN FETCH s.seller " +
            "WHERE UPPER(s.seller.name) LIKE UPPER(CONCAT('%', :name, '%')) " +
            "AND s.date BETWEEN :initialDate AND :finalDate ORDER BY s.date DESC, s.seller.name",
            countQuery = "SELECT COUNT(s) FROM Sale s JOIN s.seller")
    Page<Sale> searchReportBySellerNameAndDateBetween(String name, LocalDate initialDate, LocalDate finalDate, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT se.name AS sellerName, SUM(sa.amount) AS total FROM tb_sales sa " +
            "INNER JOIN tb_seller se ON sa.seller_id = se.id " +
            "WHERE sa.date BETWEEN :initialDate AND :finalDate " +
            "GROUP BY se.name ORDER BY se.name")
    Page<SaleSummaryMinProjection> searchSaleSummaryByDateBetween(LocalDate initialDate, LocalDate finalDate, Pageable pageable);

}
