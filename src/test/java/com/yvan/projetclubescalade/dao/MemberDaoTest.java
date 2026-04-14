package com.yvan.projetclubescalade.dao;

import com.yvan.projetclubescalade.model.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MemberDaoTest {

    @Autowired
    private MemberDao memberDao;

    private Member member1;
    private Member member2;

    @BeforeEach
    void setUp() {
        member1 = new Member();
        member1.setFirstname("Jean");
        member1.setLastname("DUPONT");
        member1.setEmail("jean.dupont@test.fr");
        member1.setPassword("password123");
        member1 = memberDao.save(member1);

        member2 = new Member();
        member2.setFirstname("Marie");
        member2.setLastname("MARTIN");
        member2.setEmail("marie.martin@test.fr");
        member2.setPassword("password456");
        member2 = memberDao.save(member2);
    }

    @Test
    void testSave() {
        assertNotNull(member1.getId());
        assertEquals("Jean", member1.getFirstname());
        assertEquals("DUPONT", member1.getLastname());
    }

    @Test
    void testSaveNewMember() {
        Member newMember = new Member();
        newMember.setFirstname("Pierre");
        newMember.setLastname("DURAND");
        newMember.setEmail("pierre.durand@test.fr");
        newMember.setPassword("pass");
        Member saved = memberDao.save(newMember);

        assertNotNull(saved.getId());
        assertEquals("Pierre", saved.getFirstname());
    }

    @Test
    void testFindById() {
        Optional<Member> found = memberDao.findById(member1.getId());
        assertTrue(found.isPresent());
        assertEquals("Jean", found.get().getFirstname());
        assertEquals("jean.dupont@test.fr", found.get().getEmail());
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Member> found = memberDao.findById(9999L);
        assertFalse(found.isPresent());
    }

    @Test
    void testFindAll() {
        List<Member> members = memberDao.findAll();
        assertEquals(2, members.size());
    }

    @Test
    void testUpdate() {
        member1.setFirstname("Jean-Pierre");
        member1.setEmail("jp.dupont@test.fr");
        memberDao.save(member1);

        Optional<Member> found = memberDao.findById(member1.getId());
        assertTrue(found.isPresent());
        assertEquals("Jean-Pierre", found.get().getFirstname());
        assertEquals("jp.dupont@test.fr", found.get().getEmail());
    }

    @Test
    void testDeleteById() {
        memberDao.deleteById(member1.getId());
        Optional<Member> found = memberDao.findById(member1.getId());
        assertFalse(found.isPresent());
        assertEquals(1, memberDao.findAll().size());
    }

    @Test
    void testFindByEmail() {
        Optional<Member> found = memberDao.findByEmail("jean.dupont@test.fr");
        assertTrue(found.isPresent());
        assertEquals("Jean", found.get().getFirstname());
    }

    @Test
    void testFindByEmailNotFound() {
        Optional<Member> found = memberDao.findByEmail("inconnu@test.fr");
        assertFalse(found.isPresent());
    }

    @Test
    void testFindByNameMatchesFirstname() {
        List<Member> found = memberDao.findByName("jean");
        assertFalse(found.isEmpty());
        assertTrue(found.stream().anyMatch(m -> m.getFirstname().equals("Jean")));
    }

    @Test
    void testFindByNameMatchesLastname() {
        List<Member> found = memberDao.findByName("dupont");
        assertFalse(found.isEmpty());
        assertTrue(found.stream().anyMatch(m -> m.getLastname().equals("DUPONT")));
    }

    @Test
    void testFindByNameCaseInsensitive() {
        List<Member> found = memberDao.findByName("JEAN");
        assertFalse(found.isEmpty());
        assertTrue(found.stream().anyMatch(m -> m.getFirstname().equals("Jean")));
    }

    @Test
    void testFindByNamePartialMatch() {
        List<Member> found = memberDao.findByName("mar");
        assertFalse(found.isEmpty());
        assertTrue(found.stream().anyMatch(m -> m.getFirstname().equals("Marie") || m.getLastname().equals("MARTIN")));
    }

    @Test
    void testFindByNameNoMatch() {
        List<Member> found = memberDao.findByName("xyz");
        assertTrue(found.isEmpty());
    }

    @Test
    void testFindByIdWithExcursions() {
        Optional<Member> found = memberDao.findByIdWithExcursions(member1.getId());
        assertTrue(found.isPresent());
        assertNotNull(found.get().getExcursions());
        assertEquals("Jean", found.get().getFirstname());
    }

    @Test
    void testFindByIdWithExcursionsNotFound() {
        Optional<Member> found = memberDao.findByIdWithExcursions(9999L);
        assertFalse(found.isPresent());
    }

    @Test
    void testFindByNameWithExcursions() {
        List<Member> found = memberDao.findByNameWithExcursions("jean");
        assertFalse(found.isEmpty());
        assertEquals("Jean", found.get(0).getFirstname());
        assertNotNull(found.get(0).getExcursions());
    }

    @Test
    void testFindByNameWithExcursionsNoMatch() {
        List<Member> found = memberDao.findByNameWithExcursions("xyz");
        assertTrue(found.isEmpty());
    }

    @Test
    void testEmailUnique() {
        Member duplicate = new Member();
        duplicate.setFirstname("Autre");
        duplicate.setLastname("PERSONNE");
        duplicate.setEmail("jean.dupont@test.fr");
        duplicate.setPassword("pass");

        assertThrows(Exception.class, () -> memberDao.save(duplicate));
    }
}