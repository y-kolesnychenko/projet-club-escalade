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
class ExcursionDaoTest {

    @Autowired
    private ExcursionDao excursionDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private MemberDao memberDao;

    private Category categoryRoche;
    private Category categoryBloc;
    private Member organizer1;
    private Member organizer2;
    private Excursion excursion1;
    private Excursion excursion2;
    private Excursion excursion3;

    @BeforeEach
    void setUp() {
        categoryRoche = new Category();
        categoryRoche.setName("Alpinisme de roche");
        categoryRoche = categoryDao.save(categoryRoche);

        categoryBloc = new Category();
        categoryBloc.setName("Escalade de bloc");
        categoryBloc = categoryDao.save(categoryBloc);

        organizer1 = new Member();
        organizer1.setFirstname("Jean");
        organizer1.setLastname("DUPONT");
        organizer1.setEmail("jean.dupont@test.fr");
        organizer1.setPassword("password");
        organizer1 = memberDao.save(organizer1);

        organizer2 = new Member();
        organizer2.setFirstname("Marie");
        organizer2.setLastname("MARTIN");
        organizer2.setEmail("marie.martin@test.fr");
        organizer2.setPassword("password");
        organizer2 = memberDao.save(organizer2);

        excursion1 = new Excursion();
        excursion1.setName("Sortie découverte Calanques");
        excursion1.setDescription("Initiation à l'escalade dans les Calanques de Marseille");
        excursion1.setWebSite("https://www.calanques.fr");
        excursion1.setDate(LocalDate.of(2026, 5, 15));
        excursion1.setOrganizer(organizer1);
        excursion1.setCategory(categoryRoche);
        excursion1 = excursionDao.save(excursion1);

        excursion2 = new Excursion();
        excursion2.setName("Session bloc Fontainebleau");
        excursion2.setDescription("Journée bloc sur les rochers de Fontainebleau");
        excursion2.setDate(LocalDate.of(2026, 6, 20));
        excursion2.setOrganizer(organizer1);
        excursion2.setCategory(categoryBloc);
        excursion2 = excursionDao.save(excursion2);

        excursion3 = new Excursion();
        excursion3.setName("Ascension matinale Verdon");
        excursion3.setDescription("Grande voie dans les Gorges du Verdon, niveau confirmé");
        excursion3.setWebSite("https://www.verdon-escalade.fr");
        excursion3.setDate(LocalDate.of(2026, 7, 10));
        excursion3.setOrganizer(organizer2);
        excursion3.setCategory(categoryRoche);
        excursion3 = excursionDao.save(excursion3);
    }

    @Test
    void testSave() {
        assertNotNull(excursion1.getId());
        assertEquals("Sortie découverte Calanques", excursion1.getName());
    }

    @Test
    void testSaveWithoutWebSite() {
        assertNull(excursion2.getWebSite());
        assertNotNull(excursion2.getId());
    }

    @Test
    void testSaveWithWebSite() {
        assertEquals("https://www.calanques.fr", excursion1.getWebSite());
    }

    @Test
    void testFindById() {
        Optional<Excursion> found = excursionDao.findById(excursion1.getId());
        assertTrue(found.isPresent());
        assertEquals("Sortie découverte Calanques", found.get().getName());
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Excursion> found = excursionDao.findById(9999L);
        assertFalse(found.isPresent());
    }

    @Test
    void testFindAll() {
        List<Excursion> excursions = excursionDao.findAll();
        assertEquals(3, excursions.size());
    }

    @Test
    void testUpdate() {
        excursion1.setName("Sortie modifiée");
        excursion1.setDescription("Description modifiée");
        excursionDao.save(excursion1);

        Optional<Excursion> found = excursionDao.findById(excursion1.getId());
        assertTrue(found.isPresent());
        assertEquals("Sortie modifiée", found.get().getName());
        assertEquals("Description modifiée", found.get().getDescription());
    }

    @Test
    void testDeleteById() {
        excursionDao.deleteById(excursion1.getId());
        Optional<Excursion> found = excursionDao.findById(excursion1.getId());
        assertFalse(found.isPresent());
        assertEquals(2, excursionDao.findAll().size());
    }

    @Test
    void testFindByCategoryId() {
        List<Excursion> found = excursionDao.findByCategoryId(categoryRoche.getId());
        assertEquals(2, found.size());
        assertTrue(found.stream().allMatch(e -> e.getCategory().getId().equals(categoryRoche.getId())));
    }

    @Test
    void testFindByCategoryIdSingleResult() {
        List<Excursion> found = excursionDao.findByCategoryId(categoryBloc.getId());
        assertEquals(1, found.size());
        assertEquals("Session bloc Fontainebleau", found.get(0).getName());
    }

    @Test
    void testFindByCategoryIdNoResult() {
        Category emptyCategory = new Category();
        emptyCategory.setName("Vide");
        emptyCategory = categoryDao.save(emptyCategory);

        List<Excursion> found = excursionDao.findByCategoryId(emptyCategory.getId());
        assertTrue(found.isEmpty());
    }

    @Test
    void testFindByOrganizerId() {
        List<Excursion> found = excursionDao.findByOrganizerId(organizer1.getId());
        assertEquals(2, found.size());
        assertTrue(found.stream().allMatch(e -> e.getOrganizer().getId().equals(organizer1.getId())));
    }

    @Test
    void testFindByOrganizerIdSingleResult() {
        List<Excursion> found = excursionDao.findByOrganizerId(organizer2.getId());
        assertEquals(1, found.size());
        assertEquals("Ascension matinale Verdon", found.get(0).getName());
    }

    @Test
    void testFindByOrganizerIdNoResult() {
        Member noExcursions = new Member();
        noExcursions.setFirstname("Pierre");
        noExcursions.setLastname("SANS");
        noExcursions.setEmail("pierre.sans@test.fr");
        noExcursions.setPassword("pass");
        noExcursions = memberDao.save(noExcursions);

        List<Excursion> found = excursionDao.findByOrganizerId(noExcursions.getId());
        assertTrue(found.isEmpty());
    }

    @Test
    void testSearchByNameOnly() {
        List<Excursion> found = excursionDao.search("sortie", null, null, null, null);
        assertEquals(1, found.size());
        assertEquals("Sortie découverte Calanques", found.get(0).getName());
    }

    @Test
    void testSearchByNameCaseInsensitive() {
        List<Excursion> found = excursionDao.search("SORTIE", null, null, null, null);
        assertEquals(1, found.size());
    }

    @Test
    void testSearchByCategoryOnly() {
        List<Excursion> found = excursionDao.search(null, categoryRoche.getId(), null, null, null);
        assertEquals(2, found.size());
    }

    @Test
    void testSearchByDateRange() {
        List<Excursion> found = excursionDao.search(null, null,
                LocalDate.of(2026, 6, 1),
                LocalDate.of(2026, 7, 31),
                null);
        assertEquals(2, found.size());
    }

    @Test
    void testSearchByStartDateOnly() {
        List<Excursion> found = excursionDao.search(null, null,
                LocalDate.of(2026, 6, 1), null, null);
        assertEquals(2, found.size());
    }

    @Test
    void testSearchByEndDateOnly() {
        List<Excursion> found = excursionDao.search(null, null,
                null, LocalDate.of(2026, 5, 31), null);
        assertEquals(1, found.size());
        assertEquals("Sortie découverte Calanques", found.get(0).getName());
    }

    @Test
    void testSearchByKeywordOnly() {
        List<Excursion> found = excursionDao.search(null, null, null, null, "calanques");
        assertEquals(1, found.size());
        assertEquals("Sortie découverte Calanques", found.get(0).getName());
    }

    @Test
    void testSearchByKeywordCaseInsensitive() {
        List<Excursion> found = excursionDao.search(null, null, null, null, "VERDON");
        assertEquals(1, found.size());
    }

    @Test
    void testSearchMultipleCriteria() {
        List<Excursion> found = excursionDao.search("sortie", categoryRoche.getId(),
                LocalDate.of(2026, 1, 1), LocalDate.of(2026, 12, 31), "calanques");
        assertEquals(1, found.size());
        assertEquals("Sortie découverte Calanques", found.get(0).getName());
    }

    @Test
    void testSearchAllNullReturnsAll() {
        List<Excursion> found = excursionDao.search(null, null, null, null, null);
        assertEquals(3, found.size());
    }

    @Test
    void testSearchNoMatch() {
        List<Excursion> found = excursionDao.search("inexistant", null, null, null, null);
        assertTrue(found.isEmpty());
    }

    @Test
    void testSearchConflictingCriteria() {
        List<Excursion> found = excursionDao.search("sortie", categoryBloc.getId(), null, null, null);
        assertTrue(found.isEmpty());
    }
}