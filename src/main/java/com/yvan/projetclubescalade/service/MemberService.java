package com.yvan.projetclubescalade.service;

import com.yvan.projetclubescalade.model.Member;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    List<Member> findAll();
    Optional<Member> findById(Long id);
    Member save(Member member);
    void deleteById(Long id);
    Optional<Member> findByIdWithExcursions(Long id);
    List<Member> findByName(String name);
    List<Member> findByNameWithExcursions(String name);
    Optional<Member> findByEmail(String email);

}
