package ma.ensa.healthcare.dao.impl;

import ma.ensa.healthcare.config.DatabaseConfig;
import ma.ensa.healthcare.dao.interfaces.IPatientDAO;
import ma.ensa.healthcare.model.enums.Sexe;
import ma.ensa.healthcare.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
/**
 * Implémentation DAO pour l'entité PATIENT
 * VERSION MINIMALE - Correspondance exacte avec la table Oracle
 */
public class PatientDAOImpl implements IPatientDAO {
=======
public class PatientDAOImpl implements IPatientDAO {

>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
    private static final Logger logger = LoggerFactory.getLogger(PatientDAOImpl.class);

    @Override
    public Patient save(Patient patient) {
<<<<<<< HEAD
        // ✅ Colonnes exactes de la table PATIENT
        String sql = "INSERT INTO PATIENT (id_patient, cin, nom, prenom, date_naissance, sexe, " +
                     "adresse, ville, code_postal, telephone, email, groupe_sanguin, allergies) " +
                     "VALUES (seq_patient.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id_patient"})) {

            ps.setString(1, patient.getCin());
            ps.setString(2, patient.getNom());
            ps.setString(3, patient.getPrenom());
            
            if (patient.getDateNaissance() != null) {
                ps.setDate(4, Date.valueOf(patient.getDateNaissance()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            
            ps.setString(5, patient.getSexe().name());
            ps.setString(6, patient.getAdresse());
            ps.setString(7, patient.getVille());
            ps.setString(8, patient.getCodePostal());
            ps.setString(9, patient.getTelephone());
            ps.setString(10, patient.getEmail());
            ps.setString(11, patient.getGroupeSanguin());
            ps.setString(12, patient.getAllergies());
            
            // date_inscription a une valeur par défaut SYSDATE, mais on peut la spécifier
            if (patient.getDateInscription() != null) {
                ps.setDate(13, Date.valueOf(patient.getDateInscription()));
            } else {
                ps.setNull(13, Types.DATE);
            }

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    patient.setId(rs.getLong(1));
                }
            }
            
            logger.info("Patient créé avec succès, ID: {}", patient.getId());
        } catch (SQLException e) {
            logger.error("Erreur save Patient: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la sauvegarde du patient", e);
=======
        String sql = "INSERT INTO PATIENT (NOM, PRENOM, CIN, ADRESSE, TELEPHONE, EMAIL, DATE_NAISSANCE, SEXE, ANTECEDENTS_MEDICAUX, DATE_CREATION) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"ID"})) {

            ps.setString(1, patient.getNom());
            ps.setString(2, patient.getPrenom());
            ps.setString(3, patient.getCin());
            ps.setString(4, patient.getAdresse());
            ps.setString(5, patient.getTelephone());
            ps.setString(6, patient.getEmail());
            
            if (patient.getDateNaissance() != null) {
                ps.setDate(7, Date.valueOf(patient.getDateNaissance()));
            } else {
                ps.setNull(7, Types.DATE);
            }
            
            ps.setString(8, patient.getSexe().name());
            ps.setString(9, patient.getAntecedentsMedicaux());
            
            if (patient.getDateCreation() != null) {
                ps.setDate(10, Date.valueOf(patient.getDateCreation()));
            } else {
                ps.setNull(10, Types.DATE);
            }

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        patient.setId(generatedKeys.getLong(1));
                    }
                }
                logger.info("Patient créé avec succès, ID: {}", patient.getId());
            }

        } catch (SQLException e) {
            logger.error("Erreur save Patient", e);
            throw new RuntimeException("Erreur SQL lors de la sauvegarde", e);
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
        }
        return patient;
    }

    @Override
    public Patient findById(Long id) {
<<<<<<< HEAD
        String sql = "SELECT * FROM PATIENT WHERE id_patient = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
=======
        String sql = "SELECT * FROM PATIENT WHERE ID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPatient(rs);
                }
            }
        } catch (SQLException e) {
<<<<<<< HEAD
            logger.error("Erreur findById Patient: {}", e.getMessage(), e);
=======
            logger.error("Erreur findById Patient", e);
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
        }
        return null;
    }

    @Override
<<<<<<< HEAD
    public Patient findByCin(String cin) {
        String sql = "SELECT * FROM PATIENT WHERE cin = ?";
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, cin);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToPatient(rs);
            }
            return null;
        } catch (SQLException e) {
            logger.error("Erreur lors de la recherche par CIN", e);
            throw new RuntimeException("Erreur findByCin", e);
        }
    }

    @Override
    public List<Patient> findAll() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM PATIENT ORDER BY nom, prenom";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
=======
    public List<Patient> findAll() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM PATIENT";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
            while (rs.next()) {
                patients.add(mapResultSetToPatient(rs));
            }
        } catch (SQLException e) {
<<<<<<< HEAD
            logger.error("Erreur findAll Patient: {}", e.getMessage(), e);
=======
            logger.error("Erreur findAll Patient", e);
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
        }
        return patients;
    }

    @Override
    public List<Patient> findByNom(String nom) {
        List<Patient> patients = new ArrayList<>();
<<<<<<< HEAD
        String sql = "SELECT * FROM PATIENT WHERE UPPER(nom) LIKE UPPER(?) " +
                     "OR UPPER(prenom) LIKE UPPER(?) ORDER BY nom, prenom"; 
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String pattern = "%" + nom + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
=======
        String sql = "SELECT * FROM PATIENT WHERE NOM LIKE ?"; 
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + nom + "%");
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    patients.add(mapResultSetToPatient(rs));
                }
            }
        } catch (SQLException e) {
<<<<<<< HEAD
            logger.error("Erreur findByNom Patient: {}", e.getMessage(), e);
=======
            logger.error("Erreur findByNom Patient", e);
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
        }
        return patients;
    }

    @Override
    public void update(Patient patient) {
<<<<<<< HEAD
        String sql = "UPDATE PATIENT SET cin = ?, nom = ?, prenom = ?, date_naissance = ?, " +
                     "sexe = ?, adresse = ?, ville = ?, code_postal = ?, telephone = ?, " +
                     "email = ?, groupe_sanguin = ?, allergies = ? WHERE id_patient = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, patient.getCin());
            ps.setString(2, patient.getNom());
            ps.setString(3, patient.getPrenom());
            
            if (patient.getDateNaissance() != null) {
                ps.setDate(4, Date.valueOf(patient.getDateNaissance()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            
            ps.setString(5, patient.getSexe().name());
            ps.setString(6, patient.getAdresse());
            ps.setString(7, patient.getVille());
            ps.setString(8, patient.getCodePostal());
            ps.setString(9, patient.getTelephone());
            ps.setString(10, patient.getEmail());
            ps.setString(11, patient.getGroupeSanguin());
            ps.setString(12, patient.getAllergies());
            ps.setLong(13, patient.getId());
=======
        String sql = "UPDATE PATIENT SET NOM=?, PRENOM=?, ADRESSE=?, TELEPHONE=?, EMAIL=? WHERE ID=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, patient.getNom());
            ps.setString(2, patient.getPrenom());
            ps.setString(3, patient.getAdresse());
            ps.setString(4, patient.getTelephone());
            ps.setString(5, patient.getEmail());
            ps.setLong(6, patient.getId());
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
            
            ps.executeUpdate();
            logger.info("Patient mis à jour ID: {}", patient.getId());
        } catch (SQLException e) {
<<<<<<< HEAD
            logger.error("Erreur update Patient: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la mise à jour du patient", e);
=======
            logger.error("Erreur update Patient", e);
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
        }
    }

    @Override
    public void delete(Long id) {
<<<<<<< HEAD
        String sql = "DELETE FROM PATIENT WHERE id_patient = ?";
=======
        String sql = "DELETE FROM PATIENT WHERE ID = ?";
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
            logger.info("Patient supprimé ID: {}", id);
        } catch (SQLException e) {
<<<<<<< HEAD
            logger.error("Erreur delete Patient: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la suppression du patient", e);
=======
            logger.error("Erreur delete Patient", e);
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
        }
    }

    private Patient mapResultSetToPatient(ResultSet rs) throws SQLException {
        Patient.PatientBuilder builder = Patient.builder()
<<<<<<< HEAD
                .id(rs.getLong("id_patient"))
                .cin(rs.getString("cin"))
                .nom(rs.getString("nom"))
                .prenom(rs.getString("prenom"))
                .sexe(Sexe.valueOf(rs.getString("sexe")))
                .adresse(rs.getString("adresse"))
                .ville(rs.getString("ville"))
                .codePostal(rs.getString("code_postal"))
                .telephone(rs.getString("telephone"))
                .email(rs.getString("email"))
                .groupeSanguin(rs.getString("groupe_sanguin"))
                .allergies(rs.getString("allergies"));
        
        Date dateNaissance = rs.getDate("date_naissance");
=======
                .id(rs.getLong("ID"))
                .nom(rs.getString("NOM"))
                .prenom(rs.getString("PRENOM"))
                .cin(rs.getString("CIN"))
                .adresse(rs.getString("ADRESSE"))
                .telephone(rs.getString("TELEPHONE"))
                .email(rs.getString("EMAIL"))
                .sexe(Sexe.valueOf(rs.getString("SEXE")))
                .antecedentsMedicaux(rs.getString("ANTECEDENTS_MEDICAUX"));
        
        Date dateNaissance = rs.getDate("DATE_NAISSANCE");
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
        if (dateNaissance != null) {
            builder.dateNaissance(dateNaissance.toLocalDate());
        }
        
<<<<<<< HEAD
        Date dateInscription = rs.getDate("date_inscription");
        if (dateInscription != null) {
            builder.dateInscription(dateInscription.toLocalDate());
=======
        Date dateCreation = rs.getDate("DATE_CREATION");
        if (dateCreation != null) {
            builder.dateCreation(dateCreation.toLocalDate());
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
        }
        
        return builder.build();
    }
}