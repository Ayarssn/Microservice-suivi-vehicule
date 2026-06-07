package com.g7suivivehicules.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.g7suivivehicules.entity.Vehicule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

/**
 * Événement Kafka publié sur le topic "vehicle.registered"
 * à chaque création d'un nouveau véhicule dans la flotte.
 *
 * Consommateurs potentiels :
 * - G4 (Coordination) : affecter le véhicule à une ligne
 * - G8 (Analytique)   : initialiser les statistiques du véhicule
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehiculeRegisteredEvent {

    /** UUID unique du véhicule nouvellement créé */
    private UUID vehiculeId;

    /** Immatriculation du véhicule (ex: "AA-123-BB") */
    private String immatriculation;

    /** Type de véhicule : BUS, TRAM, TAXI, METRO, TRAIN */
    private String type;

    /** Identifiant de la ligne affectée (null si non assigné) */
    private String ligne;

    /** Statut initial — toujours DISPONIBLE à la création */
    private String statut;

    /** UUID du conducteur associé (null si non assigné) */
    private UUID conducteurId;

    /** Horodatage de la création (ISO-8601 String pour G4 compatibility) */
    @JsonProperty("createdAt")
    private String timestamp;

    // Helper method to create from entity
    public static VehiculeRegisteredEvent fromEntity(Vehicule vehicule) {
        return VehiculeRegisteredEvent.builder()
                .vehiculeId(vehicule.getId())
                .immatriculation(vehicule.getImmatriculation())
                .type(vehicule.getType() != null ? vehicule.getType().name() : null)
                .ligne(vehicule.getLigne())
                .statut(vehicule.getStatut() != null ? vehicule.getStatut().name() : null)
                .conducteurId(vehicule.getConducteurId())
                .timestamp(LocalDateTime.now().atZone(ZoneOffset.UTC).toInstant().toString())
                .build();
    }
}
