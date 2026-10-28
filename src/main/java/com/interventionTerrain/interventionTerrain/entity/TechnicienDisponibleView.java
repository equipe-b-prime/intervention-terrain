package com.interventionTerrain.interventionTerrain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "v_technicien_disponible")
@Immutable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TechnicienDisponibleView {

    @Id
    @Column(name = "technicien_id")
    private Long technicienId;

    @Column(name = "agence_id")
    private Long agenceId;

    @Column(name = "agence_nom")
    private String agenceNom;

    @Column(name = "heures_occupees_jour")
    private Double heuresOccupeesJour;

    @Column(name = "matricule")
    private String matricule;

    @Column(name = "nb_competences_valides")
    private Integer nbCompetencesValides;

    @Column(name = "nb_interventions_jour")
    private Integer nbInterventionsJour;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;
}