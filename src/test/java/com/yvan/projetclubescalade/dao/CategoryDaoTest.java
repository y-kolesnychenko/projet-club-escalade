package com.yvan.projetclubescalade.dao;

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
class CategoryDaoTest {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private ExcursionDao excursionDao;

    private Category category1;
    private Category category2;
    private Member member;

    @BeforeEach
    void setUp() {
        category1 = new Category();
        category1.setName("Alpinisme de roche");
        category1 = categoryDao.save(category1);

        category2 = new Category();
        category2.setName("Escalade sportive");
        category2 = categoryDao.save(category2);

        member = new Member();
        member.setFirstname("Jean");
        member.setLastname("DUPONT");
        member.setEmail("jean.dupont@test.fr");
        member.setPassword("password");
        member = memberDao.save(member);
    }

    @Test
    void testSave() {
        assertNotNull(category1.getId());
        assertEquals("Alpinisme de roche", category1.getName());
    }

    @Test
    void testSaveNewCategory() {
        Category newCat = new Category();
        newCat.setName("Cascade de glace");
        Category saved = categoryDao.save(newCat);

        assertNotNull(saved.getId());
        assertEquals("Cascade de glace", saved.getName());
    }

    @Test
    void testFindById() {
        Optional<Category> found = categoryDao.findById(category1.getId());
        assertTrue(found.isPresent());
        assertEquals("Alpinisme de roche", found.get().getName());
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Category> found = categoryDao.findById(9999L);
        assertFalse(found.isPresent());
    }

    @Test
    void testFindAll() {
        List<Category> categories = categoryDao.findAll();
        assertEquals(2, categories.size());
    }

    @Test
    void testUpdate() {
        category1.setName("Alpinisme de neige");
        categoryDao.save(category1);

        Optional<Category> found = categoryDao.findById(category1.getId());
        assertTrue(found.isPresent());
        assertEquals("Alpinisme de neige", found.get().getName());
    }

    @Test
    void testDeleteById() {
        categoryDao.deleteById(category2.getId());
        Optional<Category> found = categoryDao.findById(category2.getId());
        assertFalse(found.isPresent());
        assertEquals(1, categoryDao.findAll().size());
    }

    @Test
    void testFindByName() {
        List<Category> found = categoryDao.findByName("alpinisme");
        assertFalse(found.isEmpty());
        assertTrue(found.stream().anyMatch(c -> c.getName().equals("Alpinisme de roche")));
    }

    @Test
    void testFindByNameCaseInsensitive() {
        List<Category> found = categoryDao.findByName("ESCALADE");
        assertFalse(found.isEmpty());
        assertTrue(found.stream().anyMatch(c -> c.getName().equals("Escalade sportive")));
    }

    @Test
    void testFindByNamePartialMatch() {
        List<Category> found = categoryDao.findByName("roche");
        assertEquals(1, found.size());
        assertEquals("Alpinisme de roche", found.get(0).getName());
    }

    @Test
    void testFindByNameNoMatch() {
        List<Category> found = categoryDao.findByName("natation");
        assertTrue(found.isEmpty());
    }

    @Test
    void testFindByIdWithExcursions() {
        Excursion excursion = new Excursion();
        excursion.setName("Sortie test");
        excursion.setDescription("Description test");
        excursion.setDate(LocalDate.now());
        excursion.setOrganizer(member);
        excursion.setCategory(category1);
        excursionDao.save(excursion);

        Optional<Category> found = categoryDao.findByIdWithExcursions(category1.getId());
        assertTrue(found.isPresent());
        assertEquals(1, found.get().getExcursions().size());
        assertEquals("Sortie test", found.get().getExcursions().get(0).getName());
    }

    @Test
    void testFindByIdWithExcursionsEmpty() {
        Optional<Category> found = categoryDao.findByIdWithExcursions(category2.getId());
        assertTrue(found.isPresent());
        assertTrue(found.get().getExcursions().isEmpty());
    }

    @Test
    void testFindByIdWithExcursionsNotFound() {
        Optional<Category> found = categoryDao.findByIdWithExcursions(9999L);
        assertFalse(found.isPresent());
    }

    @Test
    void testFindByNameWithExcursions() {
        Excursion excursion = new Excursion();
        excursion.setName("Sortie roche");
        excursion.setDescription("Description");
        excursion.setDate(LocalDate.now());
        excursion.setOrganizer(member);
        excursion.setCategory(category1);
        excursionDao.save(excursion);

        List<Category> found = categoryDao.findByNameWithExcursions("roche");
        assertFalse(found.isEmpty());
        assertFalse(found.get(0).getExcursions().isEmpty());
    }

    @Test
    void testFindByNameWithExcursionsNoMatch() {
        List<Category> found = categoryDao.findByNameWithExcursions("natation");
        assertTrue(found.isEmpty());
    }
}