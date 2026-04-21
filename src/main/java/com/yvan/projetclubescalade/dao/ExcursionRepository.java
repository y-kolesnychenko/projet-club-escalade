package com.yvan.projetclubescalade.dao;

import com.yvan.projetclubescalade.model.Excursion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExcursionRepository extends JpaRepository<Excursion, Long> {

    List<Excursion> findByCategoryId(Long categoryId);
    List<Excursion> findByOrganizerId(Long memberId);
    Page<Excursion> findByCategoryId(Long categoryId, Pageable pageable);

    @Query("SELECT e FROM Excursion e WHERE "+
           "(:name IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND "+
           "(:categoryId IS NULL OR e.category.id = :categoryId) AND " +
           "(:startDate IS NULL OR e.date >= :startDate) AND " +
           "(:endDate IS NULL OR e.date <= :endDate) AND " +
           "(:keyword IS NULL OR LOWER(e.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Excursion> search(@Param("name") String name,
                       @Param("categoryId") Long categoryId,
                       @Param("startDate") LocalDate startDate,
                       @Param("endDate") LocalDate endDate,
                       @Param("keyword") String keyword);

    @Query("SELECT e FROM Excursion e WHERE " +
           "(:name IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:categoryId IS NULL OR e.category.id = :categoryId) AND " +
           "(:startDate IS NULL OR e.date >= :startDate) AND " +
           "(:endDate IS NULL OR e.date <= :endDate) AND " +
           "(:keyword IS NULL OR LOWER(e.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Excursion> search(@Param("name") String name,
                           @Param("categoryId") Long categoryId,
                           @Param("startDate") LocalDate startDate,
                           @Param("endDate") LocalDate endDate,
                           @Param("keyword") String keyword,
                           Pageable pageable);
}
