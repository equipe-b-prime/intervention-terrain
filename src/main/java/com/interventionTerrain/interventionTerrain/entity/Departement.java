package com.interventionTerrain.interventionTerrain.entity;
import jakarta.persistence.*;
import lombok.*;
/**
 * Represente le departement
 */
@Entity
@Table(name = "departement")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Departement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String nom;
    @Column(nullable = false, length = 10)
    private String code;
    @ManyToOne()
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;
}
