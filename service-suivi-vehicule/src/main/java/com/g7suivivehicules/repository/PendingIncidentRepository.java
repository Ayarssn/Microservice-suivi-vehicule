package com.g7suivivehicules.repository;

import com.g7suivivehicules.entity.AlertStatus;
import com.g7suivivehicules.entity.PendingIncident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository JPA pour les incidents G9 en attente de transmission.
 */
@Repository
public interface PendingIncidentRepository extends JpaRepository<PendingIncident, String> {

    /** Retourne tous les incidents ayant un statut donné (ex: PENDING). */
    List<PendingIncident> findByStatus(AlertStatus status);

    /** Compte les incidents par statut (utile pour les métriques / logs). */
    long countByStatus(AlertStatus status);
}
