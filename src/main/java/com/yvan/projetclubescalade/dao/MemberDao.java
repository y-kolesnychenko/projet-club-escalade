package com.yvan.projetclubescalade.dao;

import com.yvan.projetclubescalade.model.Member;

import java.util.List;
import java.util.Optional;

public interface MemberDao extends GenericDao<Member,Long>{
    Optional<Member> findByIdWithExcursions(Long id);
    List<Member> findByNameWithExcursions(String name);
    Optional<Member> findByEmail(String email);
}
