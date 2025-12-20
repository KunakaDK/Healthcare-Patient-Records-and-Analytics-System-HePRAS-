package ma.ensa.healthcare;

import ma.ensa.healthcare.config.HikariCPConfig;
import ma.ensa.healthcare.config.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe principale pour tester l'initialisation de l'infrastructure Backend.
 * Step 1 : Validation de la configuration et de la connexion DB.
 */
public class Main {
    
    // Initialisation du logger SLF4J
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info(">>> Démarrage de l'application Healthcare System (Step 1) <<<");
        
        try {
            // 1. Chargement de la configuration
            logger.info("1. Initialisation du PropertyManager...");
            PropertyManager props = PropertyManager.getInstance();
            logger.info("   Mode: {}", props.isDevelopment() ? "DEVELOPMENT" : "PRODUCTION");
            
            // 2. Initialisation du Pool de Connexions
            logger.info("2. Initialisation du Pool HikariCP...");
            // L'appel à getDataSource va déclencher la création du pool
            HikariCPConfig.getDataSource();
            
            // 3. Test de connectivité
            logger.info("3. Test de connexion à Oracle Database...");
            HikariCPConfig.testConnection();
            
            logger.info(">>> INITIALISATION RÉUSSIE : Le Backend est prêt ! <<<");
            
        } catch (Exception e) {
            logger.error(">>> ERREUR FATALE : L'application n'a pas pu démarrer <<<", e);
            System.exit(1);
        }
    }
}