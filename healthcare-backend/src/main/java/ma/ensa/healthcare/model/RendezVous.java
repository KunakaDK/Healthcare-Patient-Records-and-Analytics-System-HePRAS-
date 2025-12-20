package ma.ensa.healthcare.model;

import ma.ensa.healthcare.model.enums.StatutRendezVous;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RendezVous {
    private Long id;
    private LocalDateTime dateHeure;
    private String motif;
    private StatutRendezVous statut; // PLANIFIE, CONFIRME, etc.
    
    // Relations (Clés étrangères)
    // Pour une implémentation JDBC pure, on garde souvent l'objet complet
    // pour faciliter l'accès aux infos (ex: rendezVous.getPatient().getNom())
    private Patient patient;
    private Medecin medecin;
}