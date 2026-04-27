package com.yvan.projetclubescalade.service;

import com.yvan.projetclubescalade.dao.CategoryDao;
import com.yvan.projetclubescalade.dao.ExcursionDao;
import com.yvan.projetclubescalade.dao.MemberDao;
import com.yvan.projetclubescalade.model.Category;
import com.yvan.projetclubescalade.model.Excursion;
import com.yvan.projetclubescalade.model.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ExcursionServiceTest {

    @Autowired
    private ExcursionService excursionService;

    @Autowired
    private ExcursionDao excursionDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private MemberDao memberDao;

    private Category category;
    private Member member;
    private Excursion excursion;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setName("Alpinisme");
        category = categoryDao.save(category);

        member = new Member();
        member.setFirstname("Jean");
        member.setLastname("Dupont");
        member.setEmail("jean@test.fr");
        member.setPassword("password");
        member = memberDao.save(member);

        excursion = new Excursion();
        excursion.setName("Sortie montagne");
        excursion.setDescription("Belle sortie en montagne");
        excursion.setDate(LocalDate.of(2026, 6, 15));
        excursion.setOrganizer(member);
        excursion.setCategory(category);
        excursion = excursionDao.save(excursion);
    }

    @Test
    void findAll_shouldReturnAllExcursions() {
        List<Excursion> result = excursionService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void findById_shouldReturnExcursion_whenExists() {
        Optional<Excursion> result = excursionService.findById(excursion.getId());
        assertTrue(result.isPresent());
        assertEquals("Sortie montagne", result.get().getName());
    }

    @Test
    void findById_shouldReturnEmpty_whenNotExists() {
        Optional<Excursion> result = excursionService.findById(9999L);
        assertFalse(result.isPresent());
    }

    @Test
    void save_shouldPersistExcursion() {
        Excursion newExcursion = new Excursion();
        newExcursion.setName("Nouvelle sortie");
        newExcursion.setDescription("Description");
        newExcursion.setDate(LocalDate.of(2026, 7, 20));
        newExcursion.setOrganizer(member);
        newExcursion.setCategory(category);

        Excursion saved = excursionService.save(newExcursion);

        assertNotNull(saved.getId());
        assertEquals("Nouvelle sortie", saved.getName());
    }

    @Test
    void save_shouldUpdateExistingExcursion() {
        excursion.setName("Sortie modifiée");
        excursionService.save(excursion);

        Optional<Excursion> found = excursionService.findById(excursion.getId());
        assertTrue(found.isPresent());
        assertEquals("Sortie modifiée", found.get().getName());
    }

    @Test
    void deleteById_shouldRemoveExcursion() {
        excursionService.deleteById(excursion.getId());
        Optional<Excursion> found = excursionService.findById(excursion.getId());
        assertFalse(found.isPresent());
    }

    @Test
    void findByCategoryId_shouldReturnExcursions() {
        List<Excursion> result = excursionService.findByCategoryId(category.getId());
        assertEquals(1, result.size());
        assertEquals("Sortie montagne", result.get(0).getName());
    }

    @Test
    void findByCategoryId_shouldReturnEmpty_whenNoExcursions() {
        Category emptyCategory = new Category();
        emptyCategory.setName("Vide");
        emptyCategory = categoryDao.save(emptyCategory);

        List<Excursion> result = excursionService.findByCategoryId(emptyCategory.getId());
        assertTrue(result.isEmpty());
    }

    @Test
    void findByOrganizerId_shouldReturnExcursions() {
        List<Excursion> result = excursionService.findByOrganizerId(member.getId());
        assertEquals(1, result.size());
    }

    @Test
    void findByOrganizerId_shouldReturnEmpty_whenNoExcursions() {
        Member otherMember = new Member();
        otherMember.setFirstname("Marie");
        otherMember.setLastname("Martin");
        otherMember.setEmail("marie@test.fr");
        otherMember.setPassword("pass");
        otherMember = memberDao.save(otherMember);

        List<Excursion> result = excursionService.findByOrganizerId(otherMember.getId());
        assertTrue(result.isEmpty());
    }

    @Test
    void search_shouldReturnMatchingExcursions() {
        List<Excursion> result = excursionService.search("montagne", null, null, null, null);
        assertFalse(result.isEmpty());
    }

    @Test
    void search_shouldReturnEmpty_whenNoMatch() {
        List<Excursion> result = excursionService.search("inexistant", null, null, null, null);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByCategoryId_pageable_shouldReturnPage() {
        Page<Excursion> result = excursionService.findByCategoryId(category.getId(), PageRequest.of(0, 10));
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void search_pageable_shouldReturnPage() {
        Page<Excursion> result = excursionService.search("montagne", null, null, null, null, PageRequest.of(0, 10));
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void search_byDateRange_shouldReturnMatchingExcursions() {
        LocalDate start = LocalDate.of(2026, 6, 1);
        LocalDate end = LocalDate.of(2026, 6, 30);

        List<Excursion> result = excursionService.search(null, null, start, end, null);
        assertFalse(result.isEmpty());
    }

    @Test
    void search_byCategoryId_shouldReturnMatchingExcursions() {
        List<Excursion> result = excursionService.search(null, category.getId(), null, null, null);
        assertFalse(result.isEmpty());
    }
}