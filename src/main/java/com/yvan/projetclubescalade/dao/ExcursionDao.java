package com.yvan.projetclubescalade.dao;

import com.yvan.projetclubescalade.model.Excursion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ExcursionDao extends GenericDao<Excursion,Long> {
    List<Excursion> findByCategoryId(Long categoryId);
    List<Excursion> findByOrganizerId(Long memberId);
    List<Excursion> search(String name, Long categoryId, LocalDate startDate, LocalDate endDate, String keyword);
    Page<Excursion> findByCategoryId(Long categoryId, Pageable pageable);
    Page<Excursion> search(String name, Long categoryId, LocalDate startDate, LocalDate endDate, String keyword, Pageable pageable);
}
