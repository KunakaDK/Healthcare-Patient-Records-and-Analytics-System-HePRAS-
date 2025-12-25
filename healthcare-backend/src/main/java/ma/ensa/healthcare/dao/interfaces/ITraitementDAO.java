package ma.ensa.healthcare.dao.interfaces;

import ma.ensa.healthcare.model.Traitement;
import java.util.List;

<<<<<<< HEAD
/**
 * Interface DAO pour l'entité Traitement
 */
public interface ITraitementDAO {
    /**
     * Enregistre un nouveau traitement
     */
    Traitement save(Traitement traitement);
    
    /**
     * Recherche un traitement par son ID
     */
    Traitement findById(Long id);
    
    /**
     * Récupère tous les traitements
     */
    List<Traitement> findAll();
    
    /**
     * Met à jour un traitement
     */
    void update(Traitement traitement);
    
    /**
     * Supprime un traitement
     */
    void delete(Long id);
    
    /**
     * Récupère tous les traitements d'une consultation
     */
    List<Traitement> findByConsultationId(Long consultationId);
=======
public interface ITraitementDAO {
    Traitement save(Traitement traitement);
    List<Traitement> findByConsultationId(Long consultationId);
    void delete(Long id);
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
}