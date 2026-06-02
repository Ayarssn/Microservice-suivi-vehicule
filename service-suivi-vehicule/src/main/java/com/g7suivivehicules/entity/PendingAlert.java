package com.g7suivivehicules.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Stockage local d'une alerte destinée à G5 (Notification) lorsque ce service
 * est temporairement indisponible (Circuit Breaker ouvert).
 * Le RetryScheduler retentera l'envoi toutes les 30 secondes.
 *
 * Table : pending_alerts
 */
@Entity
@Table(name = "pending_alerts", indexes = {
        @Index(name = "idx_pending_alert_status", columnList = "status"),
        @Index(name = "idx_pending_alert_vehicule", columnList = "vehiculeId")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PendingAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /** L'UUID du véhicule concerné (stocké en String pour sérialisation simple). */
    @Column(nullable = false)
    private String vehiculeId;

    /** Type de l'anomalie, ex : VITESSE_EXCESSIVE, TEMPERATURE_CRITIQUE. */
    @Column(nullable = false)
    private String typeAnomalie;

    /** Message textuel de l'alerte. */
    @Column(nullable = false, length = 1000)
    private String message;

    /** Priorité G5 : HIGH | NORMAL | LOW. */
    @Column(nullable = false)
    private String priority;

    /** Contenu JSON sérialisé de la requête G5 complète (pour un retry fidèle). */
    @Column(columnDefinition = "TEXT")
    private String payloadJson;

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

    /** Cycle de vie de l'alerte en attente. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private AlertStatus status = AlertStatus.PENDING;
}
