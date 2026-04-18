package com.yvan.projetclubescalade.service;

import com.yvan.projetclubescalade.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> findAll();
    Optional<Category> findById(Long id);
    Category save(Category category);
    void deleteById(Long id);
    Optional<Category> findByIdWithExcursions(Long Id);
    List<Category> findByNameWithExcursions(String name);
    List<Category> findByName(String name);

}
