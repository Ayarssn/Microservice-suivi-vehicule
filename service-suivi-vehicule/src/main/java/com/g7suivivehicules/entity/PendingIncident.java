package com.g7suivivehicules.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Stockage local d'un incident destiné à G9 (Gestion des Incidents) lorsque ce service
 * est temporairement indisponible (Circuit Breaker ouvert).
 * Le RetryScheduler retentera l'envoi toutes les 30 secondes.
 *
 * Table : pending_incidents
 */
@Entity
@Table(name = "pending_incidents", indexes = {
        @Index(name = "idx_pending_incident_status", columnList = "status"),
        @Index(name = "idx_pending_incident_vehicule", columnList = "vehiculeId")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PendingIncident {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /** L'UUID du véhicule concerné. */
    @Column(nullable = false)
    private String vehiculeId;

    /** Type d'incident G9 : PANNE_VEHICULE, RETARD, SECURITE, AUTRE. */
    @Column(nullable = false)
    private String typeIncident;

    /** Gravité G9 : CRITIQUE, ELEVE, MOYEN, FAIBLE. */
    @Column(nullable = false)
    private String gravite;

    /** Description textuelle de l'incident. */
    @Column(nullable = false, length = 2000)
    private String description;

    /** Latitude de l'incident. */
    private Double latitude;

    /** Longitude de l'incident. */
    private Double longitude;

    /** Date de détection de l'incident (ISO-8601). */
    @Column(nullable = false)
    private String dateDetection;

    /** Nombre de tentatives d'envoi déjà effectuées. */
    @Column(nullable = false)
    @Builder.Default
    private Integer tentatives = 0;

    /** Date de création de l'entrée dans la table. */
    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    /** Date du dernier essai d'envoi (null si aucun retry encore). */
    private LocalDateTime lastAttemptAt;

    /** Cycle de vie de l'incident en attente. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private AlertStatus status = AlertStatus.PENDING;
}
