package com.g7suivivehicules.entity;

/**
 * Cycle de vie d'une alerte/incident en attente d'envoi vers un service externe.
 * PENDING  → stocké localement, n'a pas encore été transmis
 * SENT     → transmis avec succès au service cible
 * FAILED   → échec définitif après N tentatives (abandon)
 */
public enum AlertStatus {
    PENDING,
    SENT,
    FAILED
}
