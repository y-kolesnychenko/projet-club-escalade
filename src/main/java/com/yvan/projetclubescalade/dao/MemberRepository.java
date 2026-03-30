package com.yvan.projetclubescalade.dao;

import com.yvan.projetclubescalade.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    @Query("SELECT m FROM Member m WHERE " +
           "(LOWER(m.lastname) LIKE LOWER(CONCAT('%', :name, '%'))) OR "+
           "(LOWER(m.firstname) LIKE LOWER(CONCAT('%', :name, '%')))")
    List<Member> findByName(@Param("name") String name);

    @Query("SELECT m FROM Member m LEFT JOIN FETCH m.excursions WHERE m.id = :id")
    Optional<Member> findByIdWithExcursions(@Param("id") Long id);

    @Query("SELECT DISTINCT m FROM Member m LEFT JOIN FETCH m.excursions WHERE " +
       "LOWER(m.lastname) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
       "LOWER(m.firstname) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Member> findByNameWithExcursions(@Param("name") String name);
}
