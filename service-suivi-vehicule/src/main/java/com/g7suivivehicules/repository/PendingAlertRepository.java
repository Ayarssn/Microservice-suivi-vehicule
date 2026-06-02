package com.g7suivivehicules.repository;

import com.g7suivivehicules.entity.AlertStatus;
import com.g7suivivehicules.entity.PendingAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository JPA pour les alertes G5 en attente de transmission.
 */
@Repository
public interface PendingAlertRepository extends JpaRepository<PendingAlert, String> {

    /** Retourne toutes les alertes ayant un statut donné (ex: PENDING). */
    List<PendingAlert> findByStatus(AlertStatus status);

    /** Compte les alertes par statut (utile pour les métriques / logs). */
    long countByStatus(AlertStatus status);
}
