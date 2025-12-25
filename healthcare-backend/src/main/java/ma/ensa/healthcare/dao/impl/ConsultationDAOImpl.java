package ma.ensa.healthcare.dao.impl;

import ma.ensa.healthcare.config.DatabaseConfig;
import ma.ensa.healthcare.dao.interfaces.IConsultationDAO;
import ma.ensa.healthcare.model.Consultation;
import ma.ensa.healthcare.model.RendezVous;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultationDAOImpl implements IConsultationDAO {
    private static final Logger logger = LoggerFactory.getLogger(ConsultationDAOImpl.class);

    @Override
    public Consultation save(Consultation c) {
<<<<<<< HEAD
        // ✅ Inclure date_consultation dans l'INSERT (9 paramètres)
        String sql = "INSERT INTO CONSULTATION (id_consultation, id_rdv, date_consultation, " +
                     "symptomes, diagnostic, observations, prescription, examens_demandes, tarif_consultation) " +
                     "VALUES (seq_consultation.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id_consultation"})) {
            
            // ✅ 8 PARAMÈTRES CORRECTEMENT ORDONNÉS
            ps.setLong(1, c.getRendezVous().getId());                    // 1: id_rdv
            ps.setDate(2, Date.valueOf(c.getDateConsultation()));        // 2: date_consultation
            ps.setString(3, c.getSymptomes());                           // 3: symptomes
            ps.setString(4, c.getDiagnostic());                          // 4: diagnostic
            ps.setString(5, c.getObservations());                        // 5: observations
            ps.setString(6, c.getPrescription());                        // 6: prescription
            ps.setString(7, c.getExamenesDemandes());                    // 7: examens_demandes
            ps.setBigDecimal(8, c.getTarifConsultation());               // 8: tarif_consultation

            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    c.setId(rs.getLong(1));
                }
            }
            
            logger.info("Consultation enregistrée avec succès ID: {}", c.getId());
        } catch (SQLException e) {
            logger.error("Erreur save Consultation: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de l'enregistrement de la consultation", e);
=======
        String sql = "INSERT INTO CONSULTATION (DATE_CONSULTATION, DIAGNOSTIC, TRAITEMENT_PRESCRIT, NOTES_MEDECIN, RENDEZVOUS_ID) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"ID"})) {
            
            ps.setDate(1, Date.valueOf(c.getDateConsultation()));
            ps.setString(2, c.getDiagnostic());
            ps.setString(3, c.getTraitementPrescrit());
            ps.setString(4, c.getNotesMedecin());
            ps.setLong(5, c.getRendezVous().getId());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) c.setId(rs.getLong(1));
            
            logger.info("Consultation enregistrée avec succès ID: {}", c.getId());
        } catch (SQLException e) {
            logger.error("Erreur save Consultation", e);
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
        }
        return c;
    }

    @Override
    public Consultation findById(Long id) {
<<<<<<< HEAD
        String sql = "SELECT * FROM CONSULTATION WHERE id_consultation = ?";
=======
        String sql = "SELECT * FROM CONSULTATION WHERE ID = ?";
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
<<<<<<< HEAD
            if (rs.next()) {
                return mapResultSetToConsultation(rs);
            }
        } catch (SQLException e) {
            logger.error("Erreur findById Consultation: {}", e.getMessage(), e);
        }
=======
            if (rs.next()) return mapResultSetToConsultation(rs);
        } catch (SQLException e) { logger.error("Erreur findById Consultation", e); }
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
        return null;
    }

    @Override
<<<<<<< HEAD
    public List<Consultation> findByPatientId(Long patientId) {
        List<Consultation> consultations = new ArrayList<>();
        String sql = "SELECT c.* FROM CONSULTATION c " +
                    "JOIN RENDEZ_VOUS r ON c.id_rdv = r.id_rdv " +
                    "WHERE r.id_patient = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, patientId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                consultations.add(mapResultSetToConsultation(rs));
            }
            return consultations;
        } catch (SQLException e) {
            logger.error("Erreur findByPatientId", e);
            throw new RuntimeException("Erreur findByPatientId", e);
        }
    }

    @Override
    public List<Consultation> findByMedecinId(Long medecinId) {
        List<Consultation> consultations = new ArrayList<>();
        String sql = "SELECT c.* FROM CONSULTATION c " +
                    "JOIN RENDEZ_VOUS r ON c.id_rdv = r.id_rdv " +
                    "WHERE r.id_medecin = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, medecinId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                consultations.add(mapResultSetToConsultation(rs));
            }
            return consultations;
        } catch (SQLException e) {
            logger.error("Erreur findByMedecinId", e);
            throw new RuntimeException("Erreur findByMedecinId", e);
        }
    }

    @Override
    public List<Consultation> findAll() {
        List<Consultation> list = new ArrayList<>();
        String sql = "SELECT * FROM CONSULTATION ORDER BY date_consultation DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToConsultation(rs));
            }
        } catch (SQLException e) {
            logger.error("Erreur findAll Consultation: {}", e.getMessage(), e);
        }
=======
    public List<Consultation> findAll() {
        List<Consultation> list = new ArrayList<>();
        String sql = "SELECT * FROM CONSULTATION";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) list.add(mapResultSetToConsultation(rs));
        } catch (SQLException e) { logger.error("Erreur findAll Consultation", e); }
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
        return list;
    }

    @Override
<<<<<<< HEAD
    public void update(Consultation c) {
        String sql = "UPDATE CONSULTATION SET symptomes=?, diagnostic=?, observations=?, " +
                     "prescription=?, examens_demandes=?, tarif_consultation=? " +
                     "WHERE id_consultation=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getSymptomes());
            ps.setString(2, c.getDiagnostic());
            ps.setString(3, c.getObservations());
            ps.setString(4, c.getPrescription());
            ps.setString(5, c.getExamenesDemandes());
            ps.setBigDecimal(6, c.getTarifConsultation());
            ps.setLong(7, c.getId());
            
            int rowsAffected = ps.executeUpdate();
            logger.info("Consultation mise à jour: {} lignes affectées", rowsAffected);
        } catch (SQLException e) {
            logger.error("Erreur update Consultation: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la mise à jour de la consultation", e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM CONSULTATION WHERE id_consultation=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            int rowsAffected = ps.executeUpdate();
            logger.info("Consultation supprimée: {} lignes affectées", rowsAffected);
        } catch (SQLException e) {
            logger.error("Erreur delete Consultation: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la suppression de la consultation", e);
        }
    }

    @Override
    public Consultation findByRendezVousId(Long rdvId) {
        String sql = "SELECT * FROM CONSULTATION WHERE id_rdv = ?";
=======
    public void update(Consultation c) { /* Implementation similaire à save avec SQL UPDATE */ }

    @Override
    public void delete(Long id) { /* Implementation SQL DELETE */ }

    @Override
    public Consultation findByRendezVousId(Long rdvId) {
        String sql = "SELECT * FROM CONSULTATION WHERE RENDEZVOUS_ID = ?";
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, rdvId);
            ResultSet rs = ps.executeQuery();
<<<<<<< HEAD
            if (rs.next()) {
                return mapResultSetToConsultation(rs);
            }
        } catch (SQLException e) {
            logger.error("Erreur findByRdvId: {}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * Mapper ResultSet vers objet Consultation
     */
    private Consultation mapResultSetToConsultation(ResultSet rs) throws SQLException {
        // Créer un objet RendezVous minimal (juste avec l'ID)
        RendezVous rdv = new RendezVous();
        rdv.setId(rs.getLong("id_rdv"));
        
        return Consultation.builder()
                .id(rs.getLong("id_consultation"))
                .dateConsultation(rs.getDate("date_consultation").toLocalDate())
                .symptomes(rs.getString("symptomes"))
                .diagnostic(rs.getString("diagnostic"))
                .observations(rs.getString("observations"))
                .prescription(rs.getString("prescription"))
                .examenesDemandes(rs.getString("examens_demandes"))
                .tarifConsultation(rs.getBigDecimal("tarif_consultation"))
=======
            if (rs.next()) return mapResultSetToConsultation(rs);
        } catch (SQLException e) { logger.error("Erreur findByRdvId", e); }
        return null;
    }

    private Consultation mapResultSetToConsultation(ResultSet rs) throws SQLException {
        RendezVous rdv = new RendezVous();
        rdv.setId(rs.getLong("RENDEZVOUS_ID"));
        
        return Consultation.builder()
                .id(rs.getLong("ID"))
                .dateConsultation(rs.getDate("DATE_CONSULTATION").toLocalDate())
                .diagnostic(rs.getString("DIAGNOSTIC"))
                .traitementPrescrit(rs.getString("TRAITEMENT_PRESCRIT"))
                .notesMedecin(rs.getString("NOTES_MEDECIN"))
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
                .rendezVous(rdv)
                .build();
    }
}