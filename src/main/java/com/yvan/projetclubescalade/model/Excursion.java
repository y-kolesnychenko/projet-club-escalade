package com.yvan.projetclubescalade.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "excursions")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"organizer", "category"})
public class Excursion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nom obligatoire")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Description obligatoire")
    @Column(nullable = false)
    private String description;

    @Column(nullable = true)
    private String webSite;

    @NotNull(message = "Date de sortie obligatoire")
    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member organizer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
