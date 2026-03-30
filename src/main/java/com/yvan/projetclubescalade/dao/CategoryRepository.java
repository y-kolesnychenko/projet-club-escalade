package com.yvan.projetclubescalade.dao;

import com.yvan.projetclubescalade.model.Category;
import com.yvan.projetclubescalade.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE " +
           "(LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    List<Category> findByName(@Param("name") String name);

    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.excursions WHERE c.id = :id")
    Optional<Category> findByIdWithExcursions(@Param("id") Long id);

    @Query("SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.excursions WHERE " +
       "(LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    List<Category> findByNameWithExcursions(@Param("name") String name);

}
