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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ExcursionDao excursionDao;

    @Autowired
    private MemberDao memberDao;

    private Category category1;
    private Category category2;
    private Member member;

    @BeforeEach
    void setUp() {
        category1 = new Category();
        category1.setName("Alpinisme");
        category1 = categoryDao.save(category1);

        category2 = new Category();
        category2.setName("Escalade sportive");
        category2 = categoryDao.save(category2);

        member = new Member();
        member.setFirstname("Jean");
        member.setLastname("Dupont");
        member.setEmail("jean@test.fr");
        member.setPassword("password");
        member = memberDao.save(member);
    }

    @Test
    void findAll_shouldReturnAllCategories() {
        List<Category> result = categoryService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void findById_shouldReturnCategory_whenExists() {
        Optional<Category> result = categoryService.findById(category1.getId());
        assertTrue(result.isPresent());
        assertEquals("Alpinisme", result.get().getName());
    }

    @Test
    void findById_shouldReturnEmpty_whenNotExists() {
        Optional<Category> result = categoryService.findById(9999L);
        assertFalse(result.isPresent());
    }

    @Test
    void save_shouldPersistCategory() {
        Category newCat = new Category();
        newCat.setName("Via ferrata");
        Category saved = categoryService.save(newCat);

        assertNotNull(saved.getId());
        assertEquals("Via ferrata", saved.getName());
    }

    @Test
    void save_shouldUpdateExistingCategory() {
        category1.setName("Alpinisme modifié");
        categoryService.save(category1);

        Optional<Category> found = categoryService.findById(category1.getId());
        assertTrue(found.isPresent());
        assertEquals("Alpinisme modifié", found.get().getName());
    }

    @Test
    void deleteById_shouldRemoveCategory() {
        categoryService.deleteById(category2.getId());
        Optional<Category> found = categoryService.findById(category2.getId());
        assertFalse(found.isPresent());
    }

    @Test
    void findByName_shouldReturnMatchingCategories() {
        List<Category> result = categoryService.findByName("alpin");
        assertFalse(result.isEmpty());
        assertTrue(result.stream().anyMatch(c -> c.getName().equals("Alpinisme")));
    }

    @Test
    void findByName_shouldReturnEmpty_whenNoMatch() {
        List<Category> result = categoryService.findByName("natation");
        assertTrue(result.isEmpty());
    }

    @Test
    void findByIdWithExcursions_shouldReturnCategoryWithExcursions() {
        Excursion excursion = new Excursion();
        excursion.setName("Sortie test");
        excursion.setDescription("Description");
        excursion.setDate(LocalDate.of(2026, 6, 15));
        excursion.setOrganizer(member);
        excursion.setCategory(category1);
        excursionDao.save(excursion);

        Optional<Category> result = categoryService.findByIdWithExcursions(category1.getId());
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getExcursions().size());
    }

    @Test
    void findByIdWithExcursions_shouldReturnEmpty_whenNotExists() {
        Optional<Category> result = categoryService.findByIdWithExcursions(9999L);
        assertFalse(result.isPresent());
    }

    @Test
    void findByNameWithExcursions_shouldReturnMatchingCategories() {
        Excursion excursion = new Excursion();
        excursion.setName("Sortie alpin");
        excursion.setDescription("Description");
        excursion.setDate(LocalDate.of(2026, 6, 15));
        excursion.setOrganizer(member);
        excursion.setCategory(category1);
        excursionDao.save(excursion);

        List<Category> result = categoryService.findByNameWithExcursions("alpin");
        assertFalse(result.isEmpty());
        assertFalse(result.get(0).getExcursions().isEmpty());
    }

    @Test
    void findByNameWithExcursions_shouldReturnEmpty_whenNoMatch() {
        List<Category> result = categoryService.findByNameWithExcursions("natation");
        assertTrue(result.isEmpty());
    }
}