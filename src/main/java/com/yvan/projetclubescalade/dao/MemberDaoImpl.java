package com.yvan.projetclubescalade.dao;

import com.yvan.projetclubescalade.model.Member;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MemberDaoImpl extends GenericDaoImpl<Member,Long> implements MemberDao {

    private final MemberRepository memberRepository;

    public MemberDaoImpl(MemberRepository memberRepository){
        super(memberRepository);
        this.memberRepository = memberRepository;
    }

    @Override
    public Optional<Member> findByIdWithExcursions(Long id) {
        return memberRepository.findByIdWithExcursions(id);
    }

    @Override
    public List<Member> findByName(String name) {
        return memberRepository.findByName(name);
    }

    @Override
    public List<Member> findByNameWithExcursions(String name) {
        return memberRepository.findByNameWithExcursions(name);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}
