package com.yvan.projetclubescalade.service;

import com.yvan.projetclubescalade.dao.CategoryDao;
import com.yvan.projetclubescalade.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService{

    private final CategoryDao categoryDao;

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryDao.findById(id);
    }

    @Override
    public Category save(Category category) {
        return categoryDao.save(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryDao.deleteById(id);
    }

    @Override
    public Optional<Category> findByIdWithExcursions(Long Id) {
        return categoryDao.findByIdWithExcursions(Id);
    }

    @Override
    public List<Category> findByNameWithExcursions(String name) {
        return categoryDao.findByNameWithExcursions(name);
    }

    @Override
    public List<Category> findByName(String name) {
        return categoryDao.findByName(name);
    }
}
