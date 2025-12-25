package ma.ensa.healthcare.service;

import ma.ensa.healthcare.dao.impl.MedecinDAOImpl;
import ma.ensa.healthcare.dao.interfaces.IMedecinDAO;
import ma.ensa.healthcare.exception.MedecinException;
import ma.ensa.healthcare.model.Medecin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

<<<<<<< HEAD
/**
 * Service métier pour la gestion des médecins
 */
public class MedecinService {
=======
public class MedecinService {

>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
    private static final Logger logger = LoggerFactory.getLogger(MedecinService.class);
    private final IMedecinDAO medecinDAO;

    public MedecinService() {
        this.medecinDAO = new MedecinDAOImpl();
    }

<<<<<<< HEAD
    /**
     * Crée un nouveau médecin avec validation complète
     */
    public Medecin createMedecin(Medecin medecin) {
        validateMedecin(medecin);
        
        try {
            Medecin saved = medecinDAO.save(medecin);
            logger.info("Médecin créé avec succès : {} {} (ID: {})", 
                       saved.getNom(), saved.getPrenom(), saved.getId());
            return saved;
=======
    public Medecin createMedecin(Medecin medecin) {
        validateMedecin(medecin);
        try {
            return medecinDAO.save(medecin);
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
        } catch (Exception e) {
            logger.error("Erreur lors de la création du médecin", e);
            throw new MedecinException("Impossible de créer le médecin", e);
        }
    }

<<<<<<< HEAD
    /**
     * Récupère un médecin par ID
     */
    public Medecin getMedecinById(Long id) {
        if (id == null) {
            throw new MedecinException("L'ID ne peut pas être null");
        }
        
=======
    public Medecin getMedecinById(Long id) {
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
        Medecin m = medecinDAO.findById(id);
        if (m == null) {
            throw new MedecinException("Médecin introuvable avec l'ID " + id);
        }
        return m;
    }

<<<<<<< HEAD
    /**
     * Récupère tous les médecins
     */
=======
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
    public List<Medecin> getAllMedecins() {
        return medecinDAO.findAll();
    }

<<<<<<< HEAD
    /**
     * Recherche des médecins par spécialité
     */
    public List<Medecin> getMedecinsBySpecialite(String specialite) {
        if (specialite == null || specialite.trim().isEmpty()) {
            throw new MedecinException("La spécialité ne peut pas être vide");
        }
        return medecinDAO.findBySpecialite(specialite);
    }

    /**
     * Met à jour un médecin existant
     */
    public void updateMedecin(Medecin medecin) {
        if (medecin.getId() == null) {
            throw new MedecinException("L'ID du médecin est requis pour la mise à jour");
        }
        
        validateMedecin(medecin);
        
        try {
            medecinDAO.update(medecin);
            logger.info("Médecin mis à jour : {} {} (ID: {})", 
                       medecin.getNom(), medecin.getPrenom(), medecin.getId());
        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour du médecin", e);
            throw new MedecinException("Impossible de mettre à jour le médecin", e);
        }
    }

    /**
     * Supprime un médecin
     */
=======
    public void updateMedecin(Medecin medecin) {
        validateMedecin(medecin);
        if (medecin.getId() == null) {
            throw new MedecinException("L'ID du médecin est requis pour la mise à jour");
        }
        medecinDAO.update(medecin);
    }

>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
    public void deleteMedecin(Long id) {
        if (id == null) {
            throw new MedecinException("ID invalide");
        }
<<<<<<< HEAD
        
        try {
            medecinDAO.delete(id);
            logger.info("Médecin supprimé : ID {}", id);
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression du médecin", e);
            throw new MedecinException("Impossible de supprimer le médecin", e);
        }
    }

    /**
     * Compte le nombre total de médecins
     */
    public long compterMedecins() {
        return medecinDAO.findAll().size();
    }

    /**
     * Validation complète d'un médecin
     */
=======
        medecinDAO.delete(id);
    }

    // Validation simple interne (pourrait être dans une classe Validator séparée)
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
    private void validateMedecin(Medecin medecin) {
        if (medecin == null) {
            throw new MedecinException("Le médecin ne peut pas être null");
        }
<<<<<<< HEAD
        
        // 1. Numéro d'ordre obligatoire (UNIQUE NOT NULL dans la BD)
        if (medecin.getNumeroOrdre() == null || medecin.getNumeroOrdre().trim().isEmpty()) {
            throw new MedecinException("Le numéro d'ordre est obligatoire");
        }
        
        // 2. Nom obligatoire
        if (medecin.getNom() == null || medecin.getNom().trim().isEmpty()) {
            throw new MedecinException("Le nom du médecin est obligatoire");
        }
        
        // 3. Prénom obligatoire
        if (medecin.getPrenom() == null || medecin.getPrenom().trim().isEmpty()) {
            throw new MedecinException("Le prénom du médecin est obligatoire");
        }
        
        // 4. Spécialité obligatoire
        if (medecin.getSpecialite() == null || medecin.getSpecialite().trim().isEmpty()) {
            throw new MedecinException("La spécialité est obligatoire");
        }
        
        // 5. Date d'embauche obligatoire (NOT NULL dans la BD)
        if (medecin.getDateEmbauche() == null) {
            throw new MedecinException("La date d'embauche est obligatoire");
        }
        
        // 6. Département obligatoire (FK NOT NULL dans la BD)
        if (medecin.getDepartement() == null || medecin.getDepartement().getId() == null) {
            throw new MedecinException("Le département est obligatoire");
        }
        
        // 7. Email valide (si fourni)
        if (medecin.getEmail() != null && !medecin.getEmail().trim().isEmpty()) {
            if (!medecin.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                throw new MedecinException("Format d'email invalide");
            }
        }
=======
        if (medecin.getNom() == null || medecin.getNom().trim().isEmpty()) {
            throw new MedecinException("Le nom du médecin est obligatoire");
        }
        if (medecin.getSpecialite() == null || medecin.getSpecialite().trim().isEmpty()) {
            throw new MedecinException("La spécialité est obligatoire");
        }
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
    }
}