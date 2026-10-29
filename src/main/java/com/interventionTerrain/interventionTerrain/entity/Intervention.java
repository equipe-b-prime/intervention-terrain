package com.interventionTerrain.interventionTerrain.entity;
import com.interventionTerrain.interventionTerrain.entity.Client;
import com.interventionTerrain.interventionTerrain.entity.TypeIntervention;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
/**
 * Represente les interventions
 */
@Entity
@Table(name = "intervention")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Intervention {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDate datePlanifiee;
    @Column(nullable = false, length = 50)
    private String statut;
    @Column(length = 1000)
    private String commentaire;
    @Column(length = 100)
    private String reference;
    @ManyToOne
    @JoinColumn(name = "type_intervention_id", nullable = false)
    private TypeIntervention typeIntervention;
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}
