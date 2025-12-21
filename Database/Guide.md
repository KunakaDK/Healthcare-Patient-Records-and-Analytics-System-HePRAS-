# Guide Complet d'Intégration Base de Données
## Healthcare Patient Records System 

---

## Table des matières

1. [Vue d'ensemble](#vue-densemble)
2. [Prérequis](#prérequis)
3. [Installation de la base de données](#installation-de-la-base-de-données)
4. [Structure de la base de données](#structure-de-la-base-de-données)
5. [Configuration JDBC](#configuration-jdbc)
6. [Appel des procédures stockées](#appel-des-procédures-stockées)
7. [Gestion des erreurs](#gestion-des-erreurs)
8. [Exemples de code Java](#exemples-de-code-java)
9. [Tests et validation](#tests-et-validation)
10. [Bonnes pratiques](#bonnes-pratiques)

---

##  Vue d'ensemble

### Architecture du système

```
┌─────────────────┐
│   JavaFX UI     │
│   (Frontend)    │
└────────┬────────┘
         │
┌────────▼────────┐
│   Java Backend  │
│   - Models      │
│   - DAO         │
│   - Services    │
└────────┬────────┘
         │ JDBC
┌────────▼────────┐
│  Oracle 21c DB  │
│  - Tables       │
│  - Procedures   │
│  - Triggers     │
│  - Views        │
└─────────────────┘
```

### Base de données

- **SGBD** : Oracle Database 21c Express Edition (XE)
- **Utilisateur** : `healthy`
- **Mot de passe** : `Healthy2024`
- **Tablespace** : `healthcare_data`
- **9 Tables** : PATIENT, MEDECIN, DEPARTEMENT, RENDEZ_VOUS, CONSULTATION, TRAITEMENT, MEDICAMENT, FACTURE, UTILISATEUR
- **7 Procédures stockées** : Opérations CRUD métier
- **9 Fonctions** : Calculs et validations
- **12+ Triggers** : Validation automatique et audit
- **10 Vues** : Requêtes complexes pré-calculées

---

## ✅ Prérequis

### Logiciels requis

- ✅ Oracle Database 21c XE installé et démarré
- ✅ Java JDK 11+ installé
- ✅ Maven ou Gradle (pour gestion des dépendances)
- ✅ IDE (IntelliJ IDEA, Eclipse, VS Code)
- ✅ SQL*Plus ou SQL Developer (pour tester la BD)

### Dépendances Maven

```xml
<!-- pom.xml -->
<dependencies>
    <!-- Oracle JDBC Driver -->
    <dependency>
        <groupId>com.oracle.database.jdbc</groupId>
        <artifactId>ojdbc8</artifactId>
        <version>21.9.0.0</version>
    </dependency>
    
    <!-- Connection Pool (HikariCP) - Recommandé -->
    <dependency>
        <groupId>com.zaxxer</groupId>
        <artifactId>HikariCP</artifactId>
        <version>5.0.1</version>
    </dependency>
</dependencies>
```

### Dépendances Gradle

```gradle
// build.gradle
dependencies {
    implementation 'com.oracle.database.jdbc:ojdbc8:21.9.0.0'
    implementation 'com.zaxxer:HikariCP:5.0.1'
}
```

---

## Installation de la base de données

### Ordre d'exécution des scripts SQL

**IMPORTANT : Exécuter les scripts dans cet ordre exact !**

#### **Étape 1 : Setup initial (en tant que SYSDBA)**

```bash
sqlplus sys as sysdba
# Mot de passe : votre mot de passe Oracle système
```

```sql
@00_SETUP.sql
EXIT
```

**Ce script crée :**
- ✅ Tablespace `healthcare_data`
- ✅ Utilisateur `healthy` avec mot de passe `Healthy2024`
- ✅ Privilèges complets (CONNECT, RESOURCE, DBA)

---

#### **Étape 2 : Connexion avec l'utilisateur healthy**

```bash
sqlplus healthy/Healthy2024@localhost:1521/XE
```

---

#### **Étape 3 : Création des objets de base de données**

Exécuter **dans cet ordre précis** :

| # | Fichier | Description | Obligatoire |
|---|---------|-------------|-------------|
| 1 | `01_CREATE_TABLES.sql` | Création des 9 tables + contraintes + index | ✅ OUI |
| 2 | `02_PROCEDURES.sql` | 7 procédures stockées métier | ✅ OUI |
| 3 | `03_FUNCTIONS.sql` | 9 fonctions utilitaires | ✅ OUI |
| 4 | `04_TRIGGERS.sql` | Validation automatique + audit | ✅ OUI |
| 5 | `05_VIEWS.sql` | 10 vues pour requêtes complexes | ✅ OUI |
| 6 | `06_SECURITY.sql` | Rôles RBAC (optionnel) | OPTIONNEL |
| 7 | `07_DATA.sql` | Données de test marocaines |  TEST UNIQUEMENT |

**Commandes SQL :**

```sql
-- Se connecter
sqlplus healthy/Healthy2024@localhost:1521/XE

-- Exécuter les scripts
@01_CREATE_TABLES.sql
@02_PROCEDURES.sql
@03_FUNCTIONS.sql
@04_TRIGGERS.sql
@06_VIEWS.sql
@05_DATA.sql

-- Vérification
SELECT table_name FROM user_tables ORDER BY table_name;
SELECT object_name, object_type FROM user_objects WHERE object_type IN ('PROCEDURE', 'FUNCTION', 'TRIGGER', 'VIEW');
```

---

#### **Étape 4 : Vérification de l'installation**

```sql
-- Compter les objets créés
SELECT 
    'TABLES' AS Type, COUNT(*) AS Total FROM user_tables
UNION ALL
SELECT 'PROCEDURES', COUNT(*) FROM user_procedures WHERE object_type = 'PROCEDURE'
UNION ALL
SELECT 'FUNCTIONS', COUNT(*) FROM user_procedures WHERE object_type = 'FUNCTION'
UNION ALL
SELECT 'TRIGGERS', COUNT(*) FROM user_triggers
UNION ALL
SELECT 'VIEWS', COUNT(*) FROM user_views;

-- Résultats attendus :
-- TABLES      : 10 (9 + AUDIT_LOG)
-- PROCEDURES  : 7
-- FUNCTIONS   : 9
-- TRIGGERS    : 12+
-- VIEWS       : 10

-- Vérifier les données de test
SELECT 'PATIENTS' AS Table_Name, COUNT(*) AS Nb FROM PATIENT
UNION ALL SELECT 'MEDECINS', COUNT(*) FROM MEDECIN
UNION ALL SELECT 'DEPARTEMENTS', COUNT(*) FROM DEPARTEMENT
UNION ALL SELECT 'RENDEZ_VOUS', COUNT(*) FROM RENDEZ_VOUS
UNION ALL SELECT 'CONSULTATIONS', COUNT(*) FROM CONSULTATION
UNION ALL SELECT 'FACTURES', COUNT(*) FROM FACTURE;

-- Dashboard global
SELECT * FROM V_DASHBOARD_GLOBAL;
```

---

## Structure de la base de données

### Diagramme des tables principales

```
DEPARTEMENT (1) ──< (n) MEDECIN (1) ──< (n) RENDEZ_VOUS (n) >── (1) PATIENT
                                                │
                                                │ (1:1)
                                                ▼
                                         CONSULTATION
                                                │
                                      ┌─────────┴─────────┐
                                      │                   │
                                  (1:n) TRAITEMENT   (1:1) FACTURE
                                      │
                                      │ (n:1)
                                      ▼
                                 MEDICAMENT

UTILISATEUR ──> MEDECIN (0:1)
            └──> PATIENT (0:1)
```

### Tables principales

#### **1. PATIENT**
```sql
id_patient          NUMBER(10) PK
cin                 VARCHAR2(8) UNIQUE NOT NULL  -- CIN marocain
nom                 VARCHAR2(50) NOT NULL
prenom              VARCHAR2(50) NOT NULL
date_naissance      DATE NOT NULL
sexe                CHAR(1) CHECK ('M', 'F')
telephone           VARCHAR2(20)  -- Format: +212XXXXXXXXX
email               VARCHAR2(100)
groupe_sanguin      VARCHAR2(5)   -- A+, A-, B+, B-, AB+, AB-, O+, O-
allergies           VARCHAR2(500)
ville               VARCHAR2(50)
date_inscription    DATE DEFAULT SYSDATE
```

#### **2. MEDECIN**
```sql
id_medecin          NUMBER(10) PK
numero_ordre        VARCHAR2(20) UNIQUE NOT NULL
nom                 VARCHAR2(50) NOT NULL
prenom              VARCHAR2(50) NOT NULL
specialite          VARCHAR2(50) NOT NULL
telephone           VARCHAR2(20)
email               VARCHAR2(100)
date_embauche       DATE NOT NULL
id_departement      NUMBER(10) FK -> DEPARTEMENT
```

#### **3. RENDEZ_VOUS**
```sql
id_rdv              NUMBER(10) PK
id_patient          NUMBER(10) FK -> PATIENT
id_medecin          NUMBER(10) FK -> MEDECIN
date_rdv            DATE NOT NULL
heure_debut         TIMESTAMP NOT NULL
heure_fin           TIMESTAMP NOT NULL
motif               VARCHAR2(500)
statut              VARCHAR2(20)  -- PLANIFIE, CONFIRME, TERMINE, ANNULE
salle               VARCHAR2(20)
date_creation       DATE DEFAULT SYSDATE
```

#### **4. CONSULTATION**
```sql
id_consultation     NUMBER(10) PK
id_rdv              NUMBER(10) FK -> RENDEZ_VOUS (UNIQUE)
date_consultation   DATE DEFAULT SYSDATE
symptomes           VARCHAR2(1000)
diagnostic          VARCHAR2(1000)
observations        VARCHAR2(2000)
prescription        VARCHAR2(2000)
examens_demandes    VARCHAR2(500)
tarif_consultation  NUMBER(8,2)
```

#### **5. FACTURE**
```sql
id_facture          NUMBER(10) PK
numero_facture      VARCHAR2(20) UNIQUE  -- Format: FAC-YYYY-NNNN
id_patient          NUMBER(10) FK -> PATIENT
id_consultation     NUMBER(10) FK -> CONSULTATION (UNIQUE)
date_facture        DATE DEFAULT SYSDATE
montant_consultation NUMBER(10,2)
montant_medicaments NUMBER(10,2)
montant_total       NUMBER(10,2)
montant_paye        NUMBER(10,2)
statut_paiement     VARCHAR2(20)  -- EN_ATTENTE, PAYE, PARTIEL
mode_paiement       VARCHAR2(20)  -- ESPECES, CARTE, CHEQUE, VIREMENT
date_paiement       DATE
```

### Séquences (auto-increment)

Toutes les tables ont des séquences pour générer les IDs automatiquement :

```sql
seq_patient
seq_medecin
seq_departement
seq_rdv
seq_consultation
seq_traitement
seq_medicament
seq_facture
seq_utilisateur
```

---

## Configuration JDBC

### Configuration de base

```java
// DatabaseConfig.java
public class DatabaseConfig {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "healthy";
    private static final String PASSWORD = "Healthy2024";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
```

### Configuration avec HikariCP 

```java
// DatabaseConfig.java
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseConfig {
    private static HikariDataSource dataSource;
    
    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:XE");
        config.setUsername("healthy");
        config.setPassword("Healthy2024");
        
        // Configuration du pool
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        
        // Paramètres Oracle
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        
        dataSource = new HikariDataSource(config);
    }
    
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    
    public static void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
```

### Fichier de configuration externe (application.properties)

```properties
# application.properties
db.url=jdbc:oracle:thin:@localhost:1521:XE
db.username=healthy
db.password=Healthy2024
db.pool.size=10
db.pool.timeout=30000
```

```java
// Configuration loader
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static Properties properties = new Properties();
    
    static {
        try (InputStream input = ConfigLoader.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String get(String key) {
        return properties.getProperty(key);
    }
}
```

---

## Appel des procédures stockées

### Liste des procédures disponibles

| Procédure | Description | Paramètres IN | Paramètres OUT |
|-----------|-------------|---------------|----------------|
| `SP_INSERT_PATIENT` | Insérer un patient | 11 paramètres (CIN, nom, etc.) | id_patient |
| `SP_INSERT_MEDECIN` | Insérer un médecin | 8 paramètres | id_medecin |
| `SP_CREER_RDV` | Créer un rendez-vous | 7 paramètres | id_rdv |
| `SP_MODIFIER_STATUT_RDV` | Modifier statut RDV | id_rdv, nouveau_statut | - |
| `SP_CREER_CONSULTATION` | Créer une consultation | 8 paramètres | id_consultation |
| `SP_PRESCRIRE_MEDICAMENT` | Prescrire un médicament | 6 paramètres | id_traitement |
| `SP_ENREGISTRER_PAIEMENT` | Enregistrer un paiement | 3 paramètres | - |

---

### Exemples d'appels

#### **1. Insérer un patient**

```java
public class PatientDAO {
    
    public int insertPatient(Patient patient) throws SQLException {
        String sql = "{CALL SP_INSERT_PATIENT(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        
        try (Connection conn = DatabaseConfig.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            
            // Paramètres IN
            stmt.setString(1, patient.getCin());
            stmt.setString(2, patient.getNom());
            stmt.setString(3, patient.getPrenom());
            stmt.setDate(4, new java.sql.Date(patient.getDateNaissance().getTime()));
            stmt.setString(5, patient.getSexe());
            stmt.setString(6, patient.getAdresse());
            stmt.setString(7, patient.getVille());
            stmt.setString(8, patient.getCodePostal());
            stmt.setString(9, patient.getTelephone());
            stmt.setString(10, patient.getEmail());
            stmt.setString(11, patient.getGroupeSanguin());
            stmt.setString(12, patient.getAllergies());
            
            // Paramètre OUT
            stmt.registerOutParameter(13, Types.NUMERIC);
            
            // Exécution
            stmt.execute();
            
            // Récupérer l'ID généré
            int idPatient = stmt.getInt(13);
            patient.setIdPatient(idPatient);
            
            return idPatient;
            
        } catch (SQLException e) {
            handleSQLException(e);
            throw e;
        }
    }
}
```

#### **2. Créer un rendez-vous**

```java
public class RendezVousDAO {
    
    public int creerRendezVous(RendezVous rdv) throws SQLException {
        String sql = "{CALL SP_CREER_RDV(?, ?, ?, ?, ?, ?, ?, ?)}";
        
        try (Connection conn = DatabaseConfig.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            
            stmt.setInt(1, rdv.getIdPatient());
            stmt.setInt(2, rdv.getIdMedecin());
            stmt.setDate(3, new java.sql.Date(rdv.getDateRdv().getTime()));
            stmt.setTimestamp(4, new Timestamp(rdv.getHeureDebut().getTime()));
            stmt.setTimestamp(5, new Timestamp(rdv.getHeureFin().getTime()));
            stmt.setString(6, rdv.getMotif());
            stmt.setString(7, rdv.getSalle());
            
            // Paramètre OUT
            stmt.registerOutParameter(8, Types.NUMERIC);
            
            stmt.execute();
            
            int idRdv = stmt.getInt(8);
            rdv.setIdRdv(idRdv);
            
            return idRdv;
            
        } catch (SQLException e) {
            handleSQLException(e);
            throw e;
        }
    }
}
```

#### **3. Créer une consultation**

```java
public class ConsultationDAO {
    
    public int creerConsultation(Consultation consultation) throws SQLException {
        String sql = "{CALL SP_CREER_CONSULTATION(?, ?, ?, ?, ?, ?, ?, ?)}";
        
        try (Connection conn = DatabaseConfig.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            
            stmt.setInt(1, consultation.getIdRdv());
            stmt.setString(2, consultation.getSymptomes());
            stmt.setString(3, consultation.getDiagnostic());
            stmt.setString(4, consultation.getObservations());
            stmt.setString(5, consultation.getPrescription());
            stmt.setString(6, consultation.getExamens());
            stmt.setBigDecimal(7, consultation.getTarifConsultation());
            
            stmt.registerOutParameter(8, Types.NUMERIC);
            
            stmt.execute();
            
            int idConsultation = stmt.getInt(8);
            consultation.setIdConsultation(idConsultation);
            
            return idConsultation;
            
        } catch (SQLException e) {
            handleSQLException(e);
            throw e;
        }
    }
}
```

#### **4. Enregistrer un paiement**

```java
public class FactureDAO {
    
    public void enregistrerPaiement(int idFacture, BigDecimal montant, String modePaiement) 
            throws SQLException {
        String sql = "{CALL SP_ENREGISTRER_PAIEMENT(?, ?, ?)}";
        
        try (Connection conn = DatabaseConfig.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            
            stmt.setInt(1, idFacture);
            stmt.setBigDecimal(2, montant);
            stmt.setString(3, modePaiement);
            
            stmt.execute();
            
        } catch (SQLException e) {
            handleSQLException(e);
            throw e;
        }
    }
}
```

---

## Appel des vues

Les vues simplifient les requêtes complexes. Utilisez-les comme des tables normales.

### Liste des vues disponibles

| Vue | Description | Usage |
|-----|-------------|-------|
| `V_PLANNING_MEDECIN` | Planning des médecins | Afficher le calendrier |
| `V_FACTURES_IMPAYEES` | Factures impayées/partielles | Module facturation |
| `V_HISTORIQUE_PATIENT` | Historique médical complet | Dossier patient |
| `V_TRAITEMENTS_PRESCRITS` | Traitements avec détails | Ordonnances |
| `V_STATISTIQUES_MEDECINS` | Stats par médecin | Dashboard admin |
| `V_STOCK_MEDICAMENTS` | État du stock + alertes | Gestion pharmacie |
| `V_REVENUS_JOURNALIERS` | Revenus par jour | Comptabilité |
| `V_PATIENTS_ACTIFS` | Patients actifs/inactifs | CRM |
| `V_RDV_DU_JOUR` | Rendez-vous du jour | Tableau de bord |
| `V_DASHBOARD_GLOBAL` | KPIs globaux | Dashboard principal |

### Exemples d'utilisation

```java
// Récupérer le planning d'un médecin
public List<RendezVous> getPlanningMedecin(int idMedecin, Date date) throws SQLException {
    String sql = "SELECT * FROM V_PLANNING_MEDECIN WHERE id_medecin = ? AND date_rdv = ?";
    
    try (Connection conn = DatabaseConfig.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, idMedecin);
        stmt.setDate(2, new java.sql.Date(date.getTime()));
        
        ResultSet rs = stmt.executeQuery();
        List<RendezVous> planning = new ArrayList<>();
        
        while (rs.next()) {
            RendezVous rdv = new RendezVous();
            rdv.setIdRdv(rs.getInt("id_rdv"));
            rdv.setHeureDebut(rs.getTimestamp("heure_debut"));
            rdv.setHeureFin(rs.getTimestamp("heure_fin"));
            rdv.setStatut(rs.getString("statut"));
            rdv.setNomPatient(rs.getString("nom_patient"));
            rdv.setMotif(rs.getString("motif"));
            planning.add(rdv);
        }
        
        return planning;
    }
}

// Dashboard global
public DashboardData getDashboardData() throws SQLException {
    String sql = "SELECT * FROM V_DASHBOARD_GLOBAL";
    
    try (Connection conn = DatabaseConfig.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        
        if (rs.next()) {
            DashboardData data = new DashboardData();
            data.setTotalPatients(rs.getInt("total_patients"));
            data.setTotalMedecins(rs.getInt("total_medecins"));
            data.setRdvAujourdhui(rs.getInt("rdv_aujourdhui"));
            data.setConsultationsAujourdhui(rs.getInt("consultations_aujourdhui"));
            data.setFacturesImpayees(rs.getInt("factures_impayees"));
            data.setMontantImpayeTotal(rs.getBigDecimal("montant_impaye_total"));
            data.setRevenusAujourdhui(rs.getBigDecimal("revenus_aujourdhui"));
            data.setRevenusMoisCourant(rs.getBigDecimal("revenus_mois_courant"));
            return data;
        }
        
        return null;
    }
}
```

---

## Gestion des erreurs

### Codes d'erreur Oracle personnalisés

| Code | Message | Procédure/Trigger |
|------|---------|-------------------|
| -20001 | Numero CIN deja existant | SP_INSERT_PATIENT |
| -20002 | Erreur lors de l'insertion du patient | SP_INSERT_PATIENT |
| -20004 | Numero d'ordre deja existant | SP_INSERT_MEDECIN |
| -20006 | Patient inexistant | SP_CREER_RDV |
| -20007 | Medecin inexistant | SP_CREER_RDV |
| -20009 | Le medecin n'est pas disponible a cet horaire | SP_CREER_RDV |
| -20011 | Rendez-vous inexistant | SP_MODIFIER_STATUT_RDV |
| -20015 | Une consultation existe deja pour ce rendez-vous | SP_CREER_CONSULTATION |
| -20018 | Medicament inexistant | SP_PRESCRIRE_MEDICAMENT |
| -20019 | Stock insuffisant | SP_PRESCRIRE_MEDICAMENT |
| -20021 | Facture inexistante | SP_ENREGISTRER_PAIEMENT |
| -20022 | Le montant paye depasse le montant total | SP_ENREGISTRER_PAIEMENT |
| -20101 | Format d'email invalide | TRG_VALIDATE_PATIENT |
| -20102 | Format de telephone invalide | TRG_VALIDATE_PATIENT |
| -20103 | La date de naissance doit etre dans le passe | TRG_VALIDATE_PATIENT |
| -20203 | Impossible de creer un rendez-vous dans le passe | TRG_PREVENT_PAST_RDV |

### Gestionnaire d'exceptions

```java
public class DatabaseExceptionHandler {
    
    public static String getUserFriendlyMessage(SQLException e) {
        int errorCode = Math.abs(e.getErrorCode());
        
        switch (errorCode) {
            case 20001:
                return "Ce numéro CIN existe déjà dans la base de données.";
            case 20006:
                return "Le patient spécifié n'existe pas.";
            case 20007:
                return "Le médecin spécifié n'existe pas.";
            case 20009:
                return "Le médecin n'est pas disponible à cet horaire.";
            case 20015:
                return "Une consultation existe déjà pour ce rendez-vous.";
            case 20019:
                return "Stock insuffisant pour ce médicament.";
            case 20101:
                return "Format d'email invalide.";
            case 20102:
                return "Format de téléphone invalide. Utiliser: +212XXXXXXXXX";
            case 20103:
                return "La date de naissance doit être dans le passé.";
            case 20203:
                return "Impossible de créer un rendez-vous dans le passé.";
            case 1:
                return "Une contrainte d'unicité a été violée.";
            case 2292:
                return "Impossible de supprimer : des enregistrements dépendants existent.";
            default:
                return "Erreur de base de données : " + e.getMessage();
        }
    }
    
    public static void handleSQLException(SQLException e) {
        System.err.println("SQL Error Code: " + e.getErrorCode());
        System.err.println("SQL State: " + e.getSQLState());
        System.err.println("Message: " + e.getMessage());
        e.printStackTrace();
    }
}
```

### Utilisation dans l'application

```java
try {
    patientDAO.insertPatient(patient);
    JOptionPane.showMessageDialog(null, 
        "Patient créé avec succès !", 
        "Succès", 
        JOptionPane.INFORMATION_MESSAGE);
        
} catch (SQLException e) {
    String message = DatabaseExceptionHandler.getUserFriendlyMessage(e);
    JOptionPane.showMessageDialog(null, 
        message, 
        "Erreur", 
        JOptionPane.ERROR_MESSAGE);
    DatabaseExceptionHandler.handleSQLException(e);
}
```


---

## 🧪 Tests et validation

### Script de test SQL

```sql
-- test_procedures.sql
SET SERVEROUTPUT ON;

DECLARE
    v_id_patient NUMBER;
    v_id_medecin NUMBER;
    v_id_rdv NUMBER;
BEGIN
    -- Test 1: Insérer un patient
    DBMS_OUTPUT.PUT_LINE('=== TEST 1: Inserer un patient ===');
    SP_INSERT_PATIENT(
        p_cin => 'TEST001',
        p_nom => 'TEST',
        p_prenom => 'Patient',
        p_date_naissance => TO_DATE('1990-01-01', 'YYYY-MM-DD'),
        p_sexe => 'M',
        p_adresse => 'Test Address',
        p_ville => 'Casablanca',
        p_code_postal => '20000',
        p_telephone => '+212661111111',
        p_email => 'test@test.com',
        p_groupe_sanguin => 'O+',
        p_allergies => NULL,
        p_id_patient => v_id_patient
    );
    DBMS_OUTPUT.PUT_LINE('Patient cree avec ID: ' || v_id_patient);
    
    -- Test 2: Créer un rendez-vous
    DBMS_OUTPUT.PUT_LINE('=== TEST 2: Creer un rendez-vous ===');
    SELECT id_medecin INTO v_id_medecin FROM MEDECIN WHERE ROWNUM = 1;
    
    SP_CREER_RDV(
        p_id_patient => v_id_patient,
        p_id_medecin => v_id_medecin,
        p_date_rdv => TRUNC(SYSDATE) + 1,
        p_heure_debut => TO_TIMESTAMP(TO_CHAR(SYSDATE + 1, 'YYYY-MM-DD') || ' 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        p_heure_fin => TO_TIMESTAMP(TO_CHAR(SYSDATE + 1, 'YYYY-MM-DD') || ' 10:30:00', 'YYYY-MM-DD HH24:MI:SS'),
        p_motif => 'Test rendez-vous',
        p_salle => 'Test Salle',
        p_id_rdv => v_id_rdv
    );
    DBMS_OUTPUT.PUT_LINE('Rendez-vous cree avec ID: ' || v_id_rdv);
    
    -- Nettoyage
    DELETE FROM RENDEZ_VOUS WHERE id_rdv = v_id_rdv;
    DELETE FROM PATIENT WHERE id_patient = v_id_patient;
    COMMIT;
    
    DBMS_OUTPUT.PUT_LINE('=== TESTS TERMINES AVEC SUCCES ===');
    
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('ERREUR: ' || SQLERRM);
        ROLLBACK;
END;
/
```

---

## 🎯 Checklist finale

Avant de commencer le développement backend :

- [ ] Oracle 21c XE installé et démarré
- [ ] Base de données créée (scripts 00 à 06 exécutés)
- [ ] Données de test insérées (script 05)
- [ ] Connexion JDBC testée
- [ ] Driver Oracle JDBC ajouté au projet
- [ ] HikariCP configuré (pool de connexions)
- [ ] Structure de projet créée (packages model, dao, service)
- [ ] Classes modèles créées
- [ ] Gestion des exceptions implémentée
- [ ] Tests unitaires configurés

---

## Support

En cas de problème :

1. Vérifier que Oracle est démarré : `lsnrctl status`
2. Tester la connexion : `sqlplus healthy/Healthy2024@localhost:1521/XE`
3. Vérifier les logs Oracle : `$ORACLE_HOME/diag/rdbms/xe/XE/trace/`
4. Consulter la documentation des procédures dans les fichiers SQL
5. Vérifier les codes d'erreur dans la section "Gestion des erreurs"

---

**Bon développement !**

*Guide créé pour le projet Healthcare Patient Records System - ENSA Tétouan*
*Dernière mise à jour : Décembre 2025*