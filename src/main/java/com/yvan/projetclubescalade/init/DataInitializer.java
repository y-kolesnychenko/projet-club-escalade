package com.yvan.projetclubescalade.init;

import com.yvan.projetclubescalade.dao.CategoryDao;
import com.yvan.projetclubescalade.dao.ExcursionDao;
import com.yvan.projetclubescalade.dao.MemberDao;
import com.yvan.projetclubescalade.model.Category;
import com.yvan.projetclubescalade.model.Excursion;
import com.yvan.projetclubescalade.model.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("dev")
public class DataInitializer implements CommandLineRunner {

    private final CategoryDao categoryDao;
    private final MemberDao memberDao;
    private final ExcursionDao excursionDao;

    private final MemberDataLoader memberDataLoader;
    private final CategoryDataLoader categoryDataLoader;

    private final Random random = new Random();

    private static final int NB_MEMBERS = 500;
    private static final int NB_EXCURSIONS = 5000;

    private static final String[] EXCURSION_NOMS = {
            "Sortie découverte", "Ascension matinale", "Randonnée panoramique",
            "Initiation escalade", "Session bloc", "Traversée des crêtes",
            "Sortie cascade", "Virée alpine", "Exploration verticale",
            "Sortie nocturne", "Stage perfectionnement", "Circuit des falaises",
            "Balade en altitude", "Sortie via ferrata", "Aventure glaciaire",
            "Sortie moulinette", "Grimpe en couenne", "Rando-escalade",
            "Tour des aiguilles", "Sortie grande voie"
    };

    private static final String[] EXCURSION_DESCRIPTIONS = {
            "Une sortie idéale pour les débutants souhaitant découvrir l'activité en toute sécurité.",
            "Parcours technique réservé aux grimpeurs confirmés avec passages exposés.",
            "Journée complète avec pique-nique au sommet et vue panoramique exceptionnelle.",
            "Encadrement par un guide professionnel, matériel fourni par le club.",
            "Sortie conviviale ouverte à tous les niveaux, ambiance garantie.",
            "Itinéraire sauvage hors des sentiers battus, bonne condition physique requise.",
            "Découverte d'un nouveau site récemment équipé par la fédération.",
            "Sortie d'entraînement en vue de la compétition départementale.",
            "Exploration d'un secteur peu fréquenté avec des voies variées.",
            "Sortie photo et escalade, appareil photo recommandé."
    };

    private static final String[] WEBSITES = {
            "https://www.camptocamp.org", "https://www.sortie-nature.fr",
            "https://www.montagne-escalade.com", "https://www.grimper.com",
            "https://www.escalade-aventure.com", null, null, null
    };

    @Override
    public void run(String... args) throws Exception {
        log.info("=== Début de l'initialisation des données ===");

        memberDataLoader.load();
        List<String> categoryNames = categoryDataLoader.loadCategoryNames();
        log.info("Fichiers chargés : {} prénoms, {} noms, {} catégories",
                memberDataLoader.getPrenomsCount(),
                memberDataLoader.getNomsCount(),
                categoryNames.size());

        List<Category> categories = new ArrayList<>();
        for (String name : categoryNames) {
            Category category = new Category();
            category.setName(name);
            categories.add(categoryDao.save(category));
        }
        log.info("{} catégories créées", categories.size());

        List<Member> members = new ArrayList<>();

        Member admin = new Member();
        admin.setFirstname("Admin");
        admin.setLastname("ESCALADE");
        admin.setEmail("admin@club-escalade.fr");
        admin.setPassword("admin123");
        members.add(memberDao.save(admin));

        Member fixedUser = new Member();
        fixedUser.setFirstname("Jean");
        fixedUser.setLastname("DUPONT");
        fixedUser.setEmail("jean.dupont@club-escalade.fr");
        fixedUser.setPassword("user123");
        members.add(memberDao.save(fixedUser));

        for (int i = 0; i < NB_MEMBERS; i++) {
            String prenom = memberDataLoader.randomPrenom();
            String nom = memberDataLoader.randomNom();

            Member member = new Member();
            member.setFirstname(prenom);
            member.setLastname(nom);
            member.setEmail(memberDataLoader.generateEmail(prenom, nom, i));
            member.setPassword("password" + i);
            members.add(memberDao.save(member));
        }
        log.info("{} membres créés", members.size());

        for (int i = 0; i < NB_EXCURSIONS; i++) {
            Excursion excursion = new Excursion();
            excursion.setName(EXCURSION_NOMS[random.nextInt(EXCURSION_NOMS.length)] + " #" + i);
            excursion.setDescription(EXCURSION_DESCRIPTIONS[random.nextInt(EXCURSION_DESCRIPTIONS.length)]);
            excursion.setWebSite(WEBSITES[random.nextInt(WEBSITES.length)]);
            excursion.setDate(LocalDate.now().plusDays(random.nextInt(365) - 180));
            excursion.setOrganizer(members.get(random.nextInt(members.size())));
            excursion.setCategory(categories.get(random.nextInt(categories.size())));
            excursionDao.save(excursion);
        }
        log.info("{} excursions créées", NB_EXCURSIONS);

        log.info("=== Initialisation terminée ===");
    }
}