package com.yvan.projetclubescalade.service;

import com.yvan.projetclubescalade.dao.ExcursionDao;
import com.yvan.projetclubescalade.model.Excursion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ExcursionServiceImpl implements ExcursionService{

    private final ExcursionDao excursionDao;

    @Override
    public List<Excursion> findAll() {
        return excursionDao.findAll();
    }

    @Override
    public Optional<Excursion> findById(Long id) {
        return excursionDao.findById(id);
    }

    @Override
    public Excursion save(Excursion excursion) {
        return excursionDao.save(excursion);
    }

    @Override
    public void deleteById(Long id) {
        excursionDao.deleteById(id);
    }

    @Override
    public List<Excursion> findByCategoryId(Long id) {
        return excursionDao.findByCategoryId(id);
    }

    @Override
    public List<Excursion> findByOrganizerId(Long id) {
        return excursionDao.findByOrganizerId(id);
    }

    @Override
    public List<Excursion> search(String name, Long categoryId,
                                  LocalDate startDate, LocalDate endDate, String keyword) {
        return excursionDao.search(name, categoryId, startDate, endDate, keyword);
    }
}
