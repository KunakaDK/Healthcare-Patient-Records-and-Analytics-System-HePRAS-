package ma.ensa.healthcare.dao.impl;

import ma.ensa.healthcare.config.DatabaseConfig;
import ma.ensa.healthcare.dao.interfaces.IPatientDAO;
// CORRECTION ICI : Le bon package pour l'Enum
import ma.ensa.healthcare.model.enums.Sexe;
import ma.ensa.healthcare.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAOImpl implements IPatientDAO {

    private static final Logger logger = LoggerFactory.getLogger(PatientDAOImpl.class);

    @Override
    public Patient save(Patient patient) {
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
            ps.setDate(7, Date.valueOf(patient.getDateNaissance()));
            ps.setString(8, patient.getSexe().name()); // Enregistre "M" ou "F"
            ps.setString(9, patient.getAntecedentsMedicaux());
            ps.setDate(10, Date.valueOf(patient.getDateCreation()));

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        patient.setId(generatedKeys.getLong(1));
                    }
                }
                DatabaseConfig.commit(conn); 
                logger.info("Patient créé avec succès, ID: {}", patient.getId());
            }

        } catch (SQLException e) {
            logger.error("Erreur lors de la création du patient", e);
            throw new RuntimeException("Erreur SQL", e);
        }
        return patient;
    }

    @Override
    public Patient findById(Long id) {
        String sql = "SELECT * FROM PATIENT WHERE ID = ?";
        Patient patient = null;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    patient = mapResultSetToPatient(rs);
                }
            }

        } catch (SQLException e) {
            logger.error("Erreur lors de la recherche du patient ID: " + id, e);
        }
        return patient;
    }
    
    @Override public Patient update(Patient patient) { return null; }
    @Override public void delete(Long id) {}
    @Override public List<Patient> findAll() { return new ArrayList<>(); }
    @Override public List<Patient> findByNom(String nom) { return new ArrayList<>(); }

    private Patient mapResultSetToPatient(ResultSet rs) throws SQLException {
        return Patient.builder()
                .id(rs.getLong("ID"))
                .nom(rs.getString("NOM"))
                .prenom(rs.getString("PRENOM"))
                .cin(rs.getString("CIN"))
                .adresse(rs.getString("ADRESSE"))
                .telephone(rs.getString("TELEPHONE"))
                .email(rs.getString("EMAIL"))
                .dateNaissance(rs.getDate("DATE_NAISSANCE").toLocalDate())
                .sexe(Sexe.valueOf(rs.getString("SEXE"))) // Convertit "M"/"F" en Enum
                .antecedentsMedicaux(rs.getString("ANTECEDENTS_MEDICAUX"))
                .dateCreation(rs.getDate("DATE_CREATION").toLocalDate())
                .build();
    }
}