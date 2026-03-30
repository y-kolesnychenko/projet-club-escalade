package com.yvan.projetclubescalade.dao;

import com.yvan.projetclubescalade.model.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryDaoImpl extends GenericDaoImpl<Category, Long> implements CategoryDao {

    private final CategoryRepository categoryRepository;

    public CategoryDaoImpl(CategoryRepository categoryRepository){
        super(categoryRepository);
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<Category> findByIdWithExcursions(Long Id) {
        return categoryRepository.findByIdWithExcursions(Id);
    }

    @Override
    public List<Category> findByNameWithExcursions(String name) {
        return categoryRepository.findByNameWithExcursions(name);
    }

    @Override
    public List<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }
}
