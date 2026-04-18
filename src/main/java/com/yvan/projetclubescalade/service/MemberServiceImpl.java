package com.yvan.projetclubescalade.service;


import com.yvan.projetclubescalade.dao.MemberDao;
import com.yvan.projetclubescalade.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{

    private final MemberDao memberDao;


    @Override
    public List<Member> findAll() {
        return memberDao.findAll();
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberDao.findById(id);
    }

    @Override
    public Member save(Member member) {
        return memberDao.save(member);
    }

    @Override
    public void deleteById(Long id) {
        memberDao.deleteById(id);
    }

    @Override
    public Optional<Member> findByIdWithExcursions(Long id) {
        return memberDao.findByIdWithExcursions(id);
    }

    @Override
    public List<Member> findByName(String name) {
        return memberDao.findByName(name);
    }

    @Override
    public List<Member> findByNameWithExcursions(String name) {
        return memberDao.findByNameWithExcursions(name);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberDao.findByEmail(email);
    }
}
