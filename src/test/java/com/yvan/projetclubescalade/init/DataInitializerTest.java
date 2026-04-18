package com.yvan.projetclubescalade.init;

import com.yvan.projetclubescalade.dao.CategoryDao;
import com.yvan.projetclubescalade.dao.ExcursionDao;
import com.yvan.projetclubescalade.dao.MemberDao;
import com.yvan.projetclubescalade.model.Category;
import com.yvan.projetclubescalade.model.Excursion;
import com.yvan.projetclubescalade.model.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
class DataInitializerTest {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private ExcursionDao excursionDao;

    @Test
    void testCategoriesCount() {
        List<Category> categories = categoryDao.findAll();
        assertEquals(34, categories.size(),
                "Il doit y avoir 34 catégories, trouvé : " + categories.size());
    }

    @Test
    void testMembersCount() {
        List<Member> members = memberDao.findAll();
        assertEquals(502, members.size(),
                "Il doit y avoir 502 membres, trouvé : " + members.size());
    }

    @Test
    void testExcursionsCount() {
        List<Excursion> excursions = excursionDao.findAll();
        assertEquals(5000, excursions.size(),
                "Il doit y avoir 5000 excursions, trouvé : " + excursions.size());
    }

    @Test
    void testMembersHaveValidData() {
        List<Member> members = memberDao.findAll();
        for (Member member : members) {
            assertNotNull(member.getFirstname(), "Le prénom ne doit pas être null");
            assertNotNull(member.getLastname(), "Le nom ne doit pas être null");
            assertNotNull(member.getEmail(), "L'email ne doit pas être null");
            assertTrue(member.getEmail().contains("@"), "L'email doit contenir un @");
            assertNotNull(member.getPassword(), "Le mot de passe ne doit pas être null");
        }
    }

    @Test
    void testMembersHaveUniqueEmails() {
        List<Member> members = memberDao.findAll();
        long uniqueEmails = members.stream()
                .map(Member::getEmail)
                .distinct()
                .count();
        assertEquals(members.size(), uniqueEmails, "Tous les emails doivent être uniques");
    }

    @Test
    void testCategoriesHaveValidNames() {
        List<Category> categories = categoryDao.findAll();
        for (Category category : categories) {
            assertNotNull(category.getName(), "Le nom de la catégorie ne doit pas être null");
            assertFalse(category.getName().isBlank(), "Le nom de la catégorie ne doit pas être vide");
        }
    }

    @Test
    void testExcursionsHaveValidRelations() {
        List<Excursion> excursions = excursionDao.findAll();
        for (Excursion excursion : excursions) {
            assertNotNull(excursion.getCategory(), "Chaque excursion doit avoir une catégorie");
            assertNotNull(excursion.getOrganizer(), "Chaque excursion doit avoir un organisateur");
            assertNotNull(excursion.getName(), "Chaque excursion doit avoir un nom");
            assertNotNull(excursion.getDescription(), "Chaque excursion doit avoir une description");
            assertNotNull(excursion.getDate(), "Chaque excursion doit avoir une date");
        }
    }

    @Test
    void testCategoriesHaveExcursions() {
        List<Category> categories = categoryDao.findAll();
        long categoriesWithExcursions = categories.stream()
                .filter(c -> {
                    Optional<Category> withExc = categoryDao.findByIdWithExcursions(c.getId());
                    return withExc.isPresent() && !withExc.get().getExcursions().isEmpty();
                })
                .count();
        assertTrue(categoriesWithExcursions > 0,
                "Au moins une catégorie doit avoir des excursions");
    }

    @Test
    void testSearchWorksWithLargeDataset() {
        List<Excursion> results = excursionDao.search("Sortie", null, null, null, null);
        assertFalse(results.isEmpty(), "La recherche par nom doit retourner des résultats");
    }

    @Test
    void testSearchByCategoryWorksWithLargeDataset() {
        Category firstCategory = categoryDao.findAll().get(0);
        List<Excursion> results = excursionDao.search(null, firstCategory.getId(), null, null, null);
        assertFalse(results.isEmpty(), "La recherche par catégorie doit retourner des résultats");
    }
}