package ma.ensa.healthcare.dao.interfaces;

import ma.ensa.healthcare.model.Utilisateur;
<<<<<<< HEAD
import java.util.List;

/**
 * Interface DAO pour l'entité Utilisateur
 */
public interface IUtilisateurDAO {
    /**
     * Enregistre un nouvel utilisateur
     */
    Utilisateur save(Utilisateur utilisateur);
    
    /**
     * Recherche un utilisateur par son username
     */
    Utilisateur findByUsername(String username);
    
    /**
     * Recherche un utilisateur par son ID
     */
    Utilisateur findById(Long id);
    
    /**
     * Récupère tous les utilisateurs
     */
    List<Utilisateur> findAll();
    
    /**
     * Met à jour un utilisateur
     */
    void update(Utilisateur utilisateur);
    
    /**
     * Met à jour le mot de passe d'un utilisateur
     */
    
    /**
     * Supprime un utilisateur
     */
    void delete(Long id);

    void updateDerniereConnexion(Long utilisateurId);
    void incrementerTentativesEchec(Long utilisateurId);
    void bloquerUtilisateur(Long utilisateurId);
    void debloquerUtilisateur(Long utilisateurId);
    void updatePassword(Long utilisateurId, String newPasswordHash);
=======
import java.util.Optional;

public interface IUtilisateurDAO {
    Utilisateur save(Utilisateur utilisateur);
    Utilisateur findByUsername(String username);
    Utilisateur findById(Long id);
    void updatePassword(Long id, String newPassword);
    void delete(Long id);
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
}