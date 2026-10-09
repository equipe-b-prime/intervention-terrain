package com.interventionTerrain.interventionTerrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.interventionTerrain.interventionTerrain.entity.Client;

/**
 * Repository JPA pour l'accès aux données des clients.
 * Fournit les opérations CRUD standard sur la table "client".
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}