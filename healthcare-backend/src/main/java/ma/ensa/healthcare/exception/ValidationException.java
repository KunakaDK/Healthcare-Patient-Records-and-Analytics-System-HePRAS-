package ma.ensa.healthcare.exception;

<<<<<<< HEAD
/**
 * Exception pour les erreurs de validation
 */
public class ValidationException extends RuntimeException {
    
    public ValidationException(String message) {
        super(message);
    }
    
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
=======
public class ValidationException extends HealthcareException {
    public ValidationException(String message) {
        super(message);
    }
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
}