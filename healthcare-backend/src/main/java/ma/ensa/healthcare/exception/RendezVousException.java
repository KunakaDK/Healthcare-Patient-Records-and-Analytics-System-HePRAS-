package ma.ensa.healthcare.exception;

<<<<<<< HEAD
/**
 * Exception pour les erreurs liées aux rendez-vous
 */
public class RendezVousException extends RuntimeException {
    
    public RendezVousException(String message) {
        super(message);
    }
    
    // ✅ AJOUTER ce constructeur
    public RendezVousException(String message, Throwable cause) {
        super(message, cause);
    }
=======
public class RendezVousException extends RuntimeException {
    public RendezVousException(String message) {
        super(message);
    }
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
}