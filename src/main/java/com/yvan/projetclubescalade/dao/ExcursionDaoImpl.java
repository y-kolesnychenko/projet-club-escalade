package com.yvan.projetclubescalade.dao;

import com.yvan.projetclubescalade.model.Excursion;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ExcursionDaoImpl extends GenericDaoImpl<Excursion, Long> implements ExcursionDao {

    private final ExcursionRepository excursionRepository;

    public ExcursionDaoImpl(ExcursionRepository excursionRepository){
        super(excursionRepository);
        this.excursionRepository = excursionRepository;
    }

    @Override
    public List<Excursion> findByCategoryId(Long categoryId) {
        return excursionRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Excursion> findByOrganizerId(Long memberId) {
        return excursionRepository.findByOrganizerId(memberId);
    }

    @Override
    public List<Excursion> search(String name, Long categoryId, LocalDate startDate, LocalDate endDate, String keyword) {
        return excursionRepository.search(name, categoryId, startDate, endDate, keyword);
    }
}
