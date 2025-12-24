package ma.ensa.healthcare.model;

import ma.ensa.healthcare.model.enums.StatutRendezVous;
<<<<<<< HEAD
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Modèle RendezVous - Correspond à la table RENDEZ_VOUS
 */
public class RendezVous {
    private Long id;                        // id_rdv
    private Patient patient;                // id_patient (FK NOT NULL)
    private Medecin medecin;                // id_medecin (FK NOT NULL)
    private LocalDate dateRdv;              // date_rdv (NOT NULL)
    private LocalDateTime heureDebut;       // heure_debut (TIMESTAMP NOT NULL)
    private LocalDateTime heureFin;         // heure_fin (TIMESTAMP NOT NULL)
    private String motif;                   // motif
    private StatutRendezVous statut;        // statut (DEFAULT 'PLANIFIE')
    private String salle;                   // salle
    private LocalDate dateCreation;         // date_creation (DEFAULT SYSDATE)

    // --- Constructeurs ---
    public RendezVous() {}

    public RendezVous(Long id, Patient patient, Medecin medecin, LocalDate dateRdv, 
                     LocalDateTime heureDebut, LocalDateTime heureFin, String motif, 
                     StatutRendezVous statut, String salle, LocalDate dateCreation) {
        this.id = id;
        this.patient = patient;
        this.medecin = medecin;
        this.dateRdv = dateRdv;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.motif = motif;
        this.statut = statut;
        this.salle = salle;
        this.dateCreation = dateCreation;
    }

    // --- Pattern Builder ---
=======
import java.time.LocalDateTime;

public class RendezVous {
    private Long id;
    private LocalDateTime dateHeure;
    private String motif;
    private StatutRendezVous statut;
    private Patient patient; // Relation vers Patient
    private Medecin medecin; // Relation vers Medecin

    public RendezVous() {}

    public RendezVous(Long id, LocalDateTime dateHeure, String motif, StatutRendezVous statut, Patient patient, Medecin medecin) {
        this.id = id;
        this.dateHeure = dateHeure;
        this.motif = motif;
        this.statut = statut;
        this.patient = patient;
        this.medecin = medecin;
    }

    // Builder manuel pour la cohérence
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
    public static RendezVousBuilder builder() {
        return new RendezVousBuilder();
    }

    public static class RendezVousBuilder {
        private Long id;
<<<<<<< HEAD
        private Patient patient;
        private Medecin medecin;
        private LocalDate dateRdv;
        private LocalDateTime heureDebut;
        private LocalDateTime heureFin;
        private String motif;
        private StatutRendezVous statut;
        private String salle;
        private LocalDate dateCreation;

        public RendezVousBuilder id(Long id) { this.id = id; return this; }
        public RendezVousBuilder patient(Patient patient) { this.patient = patient; return this; }
        public RendezVousBuilder medecin(Medecin medecin) { this.medecin = medecin; return this; }
        public RendezVousBuilder dateRdv(LocalDate dateRdv) { this.dateRdv = dateRdv; return this; }
        public RendezVousBuilder heureDebut(LocalDateTime heureDebut) { this.heureDebut = heureDebut; return this; }
        public RendezVousBuilder heureFin(LocalDateTime heureFin) { this.heureFin = heureFin; return this; }
        public RendezVousBuilder motif(String motif) { this.motif = motif; return this; }
        public RendezVousBuilder statut(StatutRendezVous statut) { this.statut = statut; return this; }
        public RendezVousBuilder salle(String salle) { this.salle = salle; return this; }
        public RendezVousBuilder dateCreation(LocalDate dateCreation) { this.dateCreation = dateCreation; return this; }

        public RendezVous build() {
            return new RendezVous(id, patient, medecin, dateRdv, heureDebut, heureFin, 
                                motif, statut, salle, dateCreation);
        }
    }

    // --- Getters et Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Medecin getMedecin() { return medecin; }
    public void setMedecin(Medecin medecin) { this.medecin = medecin; }

    public LocalDate getDateRdv() { return dateRdv; }
    public void setDateRdv(LocalDate dateRdv) { this.dateRdv = dateRdv; }

    public LocalDateTime getHeureDebut() { return heureDebut; }
    public void setHeureDebut(LocalDateTime heureDebut) { this.heureDebut = heureDebut; }

    public LocalDateTime getHeureFin() { return heureFin; }
    public void setHeureFin(LocalDateTime heureFin) { this.heureFin = heureFin; }
=======
        private LocalDateTime dateHeure;
        private String motif;
        private StatutRendezVous statut;
        private Patient patient;
        private Medecin medecin;

        public RendezVousBuilder id(Long id) { this.id = id; return this; }
        public RendezVousBuilder dateHeure(LocalDateTime dateHeure) { this.dateHeure = dateHeure; return this; }
        public RendezVousBuilder motif(String motif) { this.motif = motif; return this; }
        public RendezVousBuilder statut(StatutRendezVous statut) { this.statut = statut; return this; }
        public RendezVousBuilder patient(Patient patient) { this.patient = patient; return this; }
        public RendezVousBuilder medecin(Medecin medecin) { this.medecin = medecin; return this; }

        public RendezVous build() {
            return new RendezVous(id, dateHeure, motif, statut, patient, medecin);
        }
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDateHeure() { return dateHeure; }
    public void setDateHeure(LocalDateTime dateHeure) { this.dateHeure = dateHeure; }
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737

    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }

    public StatutRendezVous getStatut() { return statut; }
    public void setStatut(StatutRendezVous statut) { this.statut = statut; }

<<<<<<< HEAD
    public String getSalle() { return salle; }
    public void setSalle(String salle) { this.salle = salle; }

    public LocalDate getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDate dateCreation) { this.dateCreation = dateCreation; }
=======
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Medecin getMedecin() { return medecin; }
    public void setMedecin(Medecin medecin) { this.medecin = medecin; }
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
}