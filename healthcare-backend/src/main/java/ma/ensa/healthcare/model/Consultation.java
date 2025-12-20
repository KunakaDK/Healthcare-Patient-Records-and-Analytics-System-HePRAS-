package ma.ensa.healthcare.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Consultation {
    private Long id;
    private LocalDate dateConsultation;
    private String diagnostic;
    private String traitementPrescrit;
    private String notesMedecin;
    
    // Lien avec le Rendez-vous d'origine
    private RendezVous rendezVous;
}