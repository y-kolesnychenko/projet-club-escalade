package com.yvan.projetclubescalade.service;

import com.yvan.projetclubescalade.model.Excursion;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExcursionService {

    List<Excursion> findAll();
    Optional<Excursion> findById(Long id);
    Excursion save(Excursion excursion);
    void deleteById(Long id);
    List<Excursion> findByCategoryId(Long id);
    List<Excursion> findByOrganizerId(Long id);
    List<Excursion> search(String name, Long categoryId, LocalDate startDate,
                           LocalDate endDate, String keyword);
    Page<Excursion> findByCategoryId(Long categoryId, Pageable pageable);
    Page<Excursion> search(String name, Long categoryId, LocalDate startDate, LocalDate endDate, String keyword, Pageable pageable);
}
