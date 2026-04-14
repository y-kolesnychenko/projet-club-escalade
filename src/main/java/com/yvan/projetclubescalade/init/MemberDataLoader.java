package com.yvan.projetclubescalade.init;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class MemberDataLoader {

    private final Random random = new Random();
    private List<String> prenoms;
    private List<String> noms;

    public void load() throws Exception {
        this.prenoms = loadPrenoms();
        this.noms = loadNoms();
    }

    public int getPrenomsCount() { return prenoms.size(); }
    public int getNomsCount() { return noms.size(); }

    public String randomPrenom() {
        return prenoms.get(random.nextInt(prenoms.size()));
    }

    public String randomNom() {
        return noms.get(random.nextInt(noms.size()));
    }

    public String generateEmail(String prenom, String nom, int index) {
        String cleanPrenom = prenom.toLowerCase().replaceAll("[^a-z]", "");
        String cleanNom = nom.toLowerCase().replaceAll("[^a-z]", "");
        return cleanPrenom + "." + cleanNom + index + "@club-escalade.fr";
    }

    private List<String> loadPrenoms() throws Exception {
        ClassPathResource resource = new ClassPathResource("static/prenoms.csv");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            return reader.lines()
                    .skip(1)
                    .map(line -> line.split(";")[1])
                    .distinct()
                    .map(p -> p.charAt(0) + p.substring(1).toLowerCase())
                    .collect(Collectors.toList());
        }
    }

    private List<String> loadNoms() throws Exception {
        ClassPathResource resource = new ClassPathResource("static/noms.txt");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            return reader.lines()
                    .skip(1)
                    .map(line -> line.split("\t")[0])
                    .distinct()
                    .collect(Collectors.toList());
        }
    }
}