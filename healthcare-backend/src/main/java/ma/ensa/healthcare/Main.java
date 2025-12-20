package ma.ensa.healthcare;

import ma.ensa.healthcare.config.HikariCPConfig;
import ma.ensa.healthcare.dao.impl.PatientDAOImpl;
import ma.ensa.healthcare.dao.interfaces.IPatientDAO;
import ma.ensa.healthcare.model.enums.Sexe;
import ma.ensa.healthcare.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class Main {
    
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info(">>> Démarrage du Test DAO (Step 2) <<<");
        
        try {
            // Initialisation du Pool
            HikariCPConfig.getDataSource();
            
            // 1. Instanciation du DAO
            IPatientDAO patientDAO = new PatientDAOImpl();
            
            // 2. Création d'un objet Patient (Données fictives)
            logger.info("Création d'un nouveau patient...");
            Patient nouveauPatient = Patient.builder()
                    .nom("EL ALAMI")
                    .prenom("Ahmed")
                    .cin("AB123456")
                    .adresse("123 Av. Mohammed V, Tetouan")
                    .telephone("0611223344")
                    .email("ahmed.alami@example.com")
                    .dateNaissance(LocalDate.of(1985, 5, 15))
                    .sexe(Sexe.M)
                    .antecedentsMedicaux("Allergie à la pénicilline")
                    .dateCreation(LocalDate.now())
                    .build();

            // 3. Sauvegarde en Base de Données
            Patient savedPatient = patientDAO.save(nouveauPatient);
            logger.info("Patient enregistré avec succès ! ID attribué : {}", savedPatient.getId());

            // 4. Lecture pour vérification
            Patient retrievedPatient = patientDAO.findById(savedPatient.getId());
            logger.info("Lecture depuis la DB : {} {}", retrievedPatient.getNom(), retrievedPatient.getPrenom());
            
        } catch (Exception e) {
            logger.error("ERREUR lors du test DAO", e);
        }
    }
}