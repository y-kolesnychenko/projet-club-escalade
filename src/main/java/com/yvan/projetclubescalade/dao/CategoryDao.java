package com.yvan.projetclubescalade.dao;

import com.yvan.projetclubescalade.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDao extends GenericDao<Category, Long>{
    Optional<Category> findByIdWithExcursions(Long Id);
    List<Category> findByNameWithExcursions(String name);
}
