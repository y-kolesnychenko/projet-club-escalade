package com.yvan.projetclubescalade.dao;

import com.yvan.projetclubescalade.model.Excursion;

import java.time.LocalDate;
import java.util.List;

public interface ExcursionDao extends GenericDao<Excursion,Long> {
    List<Excursion> findByCategoryId(Long categoryId);
    List<Excursion> findByOrganizerId(Long memberId);
    List<Excursion> search(String name, Long categoryId, LocalDate startDate, LocalDate endDate, String keyword);
}
