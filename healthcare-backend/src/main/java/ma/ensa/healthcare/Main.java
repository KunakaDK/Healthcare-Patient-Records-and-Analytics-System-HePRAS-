package ma.ensa.healthcare;

import java.time.LocalDate;
import java.time.LocalDateTime;

import ma.ensa.healthcare.config.HikariCPConfig;
import ma.ensa.healthcare.dao.impl.MedecinDAOImpl;
import ma.ensa.healthcare.dao.impl.PatientDAOImpl;
import ma.ensa.healthcare.dao.impl.RendezVousDAOImpl;
import ma.ensa.healthcare.dao.interfaces.IMedecinDAO;
import ma.ensa.healthcare.dao.interfaces.IPatientDAO;
import ma.ensa.healthcare.dao.interfaces.IRendezVousDAO;
import ma.ensa.healthcare.facade.ConsultationFacade;
import ma.ensa.healthcare.model.Consultation;
import ma.ensa.healthcare.model.Medecin;
import ma.ensa.healthcare.model.Patient;
import ma.ensa.healthcare.model.RendezVous;
import ma.ensa.healthcare.model.enums.Sexe;
import ma.ensa.healthcare.model.enums.StatutRendezVous;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== INITIALISATION DU SYSTÈME HEALTHCARE ===");
        
        // 1. Test de connexion à Oracle
        HikariCPConfig.testConnection();

        try {
            // Initialisation des DAOs et Facades
            IPatientDAO patientDAO = new PatientDAOImpl();
            IMedecinDAO medecinDAO = new MedecinDAOImpl();
            IRendezVousDAO rdvDAO = new RendezVousDAOImpl();
            ConsultationFacade consultationFacade = new ConsultationFacade();

            // --- ÉTAPE 1 : CRÉATION DU PATIENT ---
            System.out.println("\n[1] Enregistrement d'un nouveau patient...");
Patient patient = Patient.builder()
        .nom("Alami")
        .prenom("Yassine")
        .cin("K123456")
        .email("y.alami@email.com")
        .dateNaissance(LocalDate.of(1990, 5, 15))
        .sexe(Sexe.M) // <-- AJOUTEZ CETTE LIGNE (Vérifiez le nom exact dans votre enum Sexe)
        .telephone("0612345678")
        .build();
patientDAO.save(patient);

            // --- ÉTAPE 2 : CRÉATION DU MÉDECIN ---
            System.out.println("\n[2] Enregistrement d'un médecin...");
            Medecin medecin = Medecin.builder()
                    .nom("Benjelloun")
                    .prenom("Sara")
                    .specialite("Cardiologie")
                    .build();
            medecinDAO.save(medecin);
            System.out.println("Médecin créé avec ID : " + medecin.getId());

            // --- ÉTAPE 3 : PLANIFICATION D'UN RENDEZ-VOUS ---
            System.out.println("\n[3] Planification d'un rendez-vous...");
            RendezVous rdv = RendezVous.builder()
                    .dateHeure(LocalDateTime.now().plusDays(1))
                    .statut(StatutRendezVous.CONFIRME)
                    .motif("Contrôle annuel")
                    .patient(patient)
                    .medecin(medecin)
                    .build();
            rdvDAO.save(rdv);
            System.out.println("Rendez-vous fixé pour le : " + rdv.getDateHeure());

            // --- ÉTAPE 4 : CONSULTATION ET FACTURATION AUTOMATIQUE ---
            System.out.println("\n[4] Réalisation de la consultation...");
            Consultation consultation = Consultation.builder()
                    .dateConsultation(LocalDate.now())
                    .diagnostic("Hypertension légère détectée")
                    .traitementPrescrit("Amlodipine 5mg")
                    .notesMedecin("Prévoir un test d'effort dans 3 mois")
                    .rendezVous(rdv)
                    .build();

            // Utilisation de la Facade pour l'atomicité (Consultation + Facture)
            consultationFacade.terminerConsultation(consultation, 300.00); 
            System.out.println("Consultation terminée et Facture de 300.00 DH générée.");

            // --- ÉTAPE 5 : VÉRIFICATION FINALE ---
            System.out.println("\n[5] Résumé du dossier patient :");
            System.out.println("--------------------------------------------------");
            System.out.println("Patient : " + patient.getNom() + " " + patient.getPrenom());
            System.out.println("Diagnostic : " + consultation.getDiagnostic());
            System.out.println("Statut de facturation : EN_ATTENTE");
            System.out.println("--------------------------------------------------");

        } catch (Exception e) {
            System.err.println("ERREUR LORS DU TEST : " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("\n=== FIN DU TEST DU SYSTÈME ===");
        }
    }
}