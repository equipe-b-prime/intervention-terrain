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
@Table(name = "v_taux_reussite_agence")
@Immutable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TauxReussiteView {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "agence_id")
    private Long agenceId;

    @Column(name = "agence_nom")
    private String agenceNom;

    @Column(name = "type_intervention_id")
    private Long typeInterventionId;

    @Column(name = "type_intervention_nom")
    private String typeInterventionNom;

    @Column(name = "mois")
    private String mois;

    @Column(name = "nb_terminee")
    private Long nbTerminee;

    @Column(name = "nb_echouee")
    private Long nbEchouee;
}