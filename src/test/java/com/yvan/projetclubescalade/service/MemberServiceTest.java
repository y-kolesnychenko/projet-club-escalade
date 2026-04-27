package com.yvan.projetclubescalade.service;

import com.yvan.projetclubescalade.dao.ExcursionDao;
import com.yvan.projetclubescalade.dao.MemberDao;
import com.yvan.projetclubescalade.dao.CategoryDao;
import com.yvan.projetclubescalade.model.Category;
import com.yvan.projetclubescalade.model.Excursion;
import com.yvan.projetclubescalade.model.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ExcursionDao excursionDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member();
        member.setFirstname("Jean");
        member.setLastname("Dupont");
        member.setEmail("jean@test.fr");
        member.setPassword("password123");
        member = memberDao.save(member);
    }

    @Test
    void findAll_shouldReturnAllMembers() {
        List<Member> result = memberService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void findById_shouldReturnMember_whenExists() {
        Optional<Member> result = memberService.findById(member.getId());
        assertTrue(result.isPresent());
        assertEquals("Jean", result.get().getFirstname());
    }

    @Test
    void findById_shouldReturnEmpty_whenNotExists() {
        Optional<Member> result = memberService.findById(9999L);
        assertFalse(result.isPresent());
    }

    @Test
    void save_shouldEncodePassword_whenNewMember() {
        Member newMember = new Member();
        newMember.setFirstname("Marie");
        newMember.setLastname("Martin");
        newMember.setEmail("marie@test.fr");
        newMember.setPassword("rawpassword");

        Member saved = memberService.save(newMember);

        assertNotNull(saved.getId());
        assertNotEquals("rawpassword", saved.getPassword());
        assertTrue(passwordEncoder.matches("rawpassword", saved.getPassword()));
    }

    @Test
    void save_shouldNotReEncodePassword_whenExistingMember() {
        String currentPassword = member.getPassword();
        member.setFirstname("Jean-Pierre");

        Member saved = memberService.save(member);

        assertEquals(currentPassword, saved.getPassword());
        assertEquals("Jean-Pierre", saved.getFirstname());
    }

    @Test
    void deleteById_shouldRemoveMember() {
        memberService.deleteById(member.getId());
        Optional<Member> found = memberService.findById(member.getId());
        assertFalse(found.isPresent());
    }

    @Test
    void findByEmail_shouldReturnMember_whenExists() {
        Optional<Member> result = memberService.findByEmail("jean@test.fr");
        assertTrue(result.isPresent());
        assertEquals("Dupont", result.get().getLastname());
    }

    @Test
    void findByEmail_shouldReturnEmpty_whenNotExists() {
        Optional<Member> result = memberService.findByEmail("inconnu@test.fr");
        assertFalse(result.isPresent());
    }

    @Test
    void findByName_shouldReturnMatchingMembers() {
        List<Member> result = memberService.findByName("Jean");
        assertFalse(result.isEmpty());
    }

    @Test
    void findByName_shouldReturnEmpty_whenNoMatch() {
        List<Member> result = memberService.findByName("Inexistant");
        assertTrue(result.isEmpty());
    }

    @Test
    void findByIdWithExcursions_shouldReturnMemberWithExcursions() {
        Category category = new Category();
        category.setName("Alpinisme");
        category = categoryDao.save(category);

        Excursion excursion = new Excursion();
        excursion.setName("Sortie");
        excursion.setDescription("Description");
        excursion.setDate(LocalDate.of(2026, 6, 15));
        excursion.setOrganizer(member);
        excursion.setCategory(category);
        excursionDao.save(excursion);

        Optional<Member> result = memberService.findByIdWithExcursions(member.getId());
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getExcursions().size());
    }

    @Test
    void findByNameWithExcursions_shouldReturnMatchingMembers() {
        Category category = new Category();
        category.setName("Escalade");
        category = categoryDao.save(category);

        Excursion excursion = new Excursion();
        excursion.setName("Sortie escalade");
        excursion.setDescription("Description");
        excursion.setDate(LocalDate.of(2026, 7, 10));
        excursion.setOrganizer(member);
        excursion.setCategory(category);
        excursionDao.save(excursion);

        List<Member> result = memberService.findByNameWithExcursions("Jean");
        assertFalse(result.isEmpty());
        assertFalse(result.get(0).getExcursions().isEmpty());
    }

    @Test
    void register_shouldEncodePasswordAndSave() {
        Member newMember = new Member();
        newMember.setFirstname("Pierre");
        newMember.setLastname("Durand");
        newMember.setEmail("pierre@test.fr");
        newMember.setPassword("monmotdepasse");

        Member registered = memberService.register(newMember);

        assertNotNull(registered.getId());
        assertNotEquals("monmotdepasse", registered.getPassword());
        assertTrue(passwordEncoder.matches("monmotdepasse", registered.getPassword()));
    }
}