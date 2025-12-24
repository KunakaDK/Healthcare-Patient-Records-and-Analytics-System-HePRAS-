package ma.ensa.healthcare.dao.impl;

import ma.ensa.healthcare.config.DatabaseConfig;
import ma.ensa.healthcare.dao.interfaces.IMedecinDAO;
import ma.ensa.healthcare.model.Medecin;
<<<<<<< HEAD
import ma.ensa.healthcare.model.Departement;
=======
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
/**
 * Implémentation DAO pour l'entité MEDECIN
 * VERSION MINIMALE - Seulement les méthodes essentielles
 */
public class MedecinDAOImpl implements IMedecinDAO {
=======
public class MedecinDAOImpl implements IMedecinDAO {
    
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
    private static final Logger logger = LoggerFactory.getLogger(MedecinDAOImpl.class);

    @Override
    public Medecin save(Medecin medecin) {
<<<<<<< HEAD
        String sql = "INSERT INTO MEDECIN (id_medecin, numero_ordre, nom, prenom, " +
                     "specialite, telephone, email, date_embauche, id_departement) " +
                     "VALUES (seq_medecin.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id_medecin"})) {
            
            ps.setString(1, medecin.getNumeroOrdre());
            ps.setString(2, medecin.getNom());
            ps.setString(3, medecin.getPrenom());
            ps.setString(4, medecin.getSpecialite());
            ps.setString(5, medecin.getTelephone());
            ps.setString(6, medecin.getEmail());
            ps.setDate(7, Date.valueOf(medecin.getDateEmbauche()));
            
            if (medecin.getDepartement() != null && medecin.getDepartement().getId() != null) {
                ps.setLong(8, medecin.getDepartement().getId());
            } else {
                ps.setNull(8, Types.NUMERIC);
            }
            
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    medecin.setId(rs.getLong(1));
                }
            }
            
            logger.info("Médecin enregistré : Dr. {} {} (ID: {})", 
                       medecin.getNom(), medecin.getPrenom(), medecin.getId());
        } catch (SQLException e) {
            logger.error("Erreur save Medecin: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la sauvegarde du médecin", e);
=======
        String sql = "INSERT INTO MEDECIN (NOM, PRENOM, SPECIALITE, EMAIL, TELEPHONE) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"ID"})) {
            
            ps.setString(1, medecin.getNom());
            ps.setString(2, medecin.getPrenom());
            ps.setString(3, medecin.getSpecialite());
            ps.setString(4, medecin.getEmail());
            ps.setString(5, medecin.getTelephone());
            
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                medecin.setId(rs.getLong(1));
            }
            logger.info("Médecin enregistré avec succès ID: {}", medecin.getId());
        } catch (SQLException e) {
            logger.error("Erreur save Medecin", e);
            throw new RuntimeException(e);
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
        }
        return medecin;
    }

    @Override
    public Medecin findById(Long id) {
<<<<<<< HEAD
        String sql = "SELECT * FROM MEDECIN WHERE id_medecin = ?";
=======
        String sql = "SELECT * FROM MEDECIN WHERE ID = ?";
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMedecin(rs);
                }
            }
        } catch (SQLException e) {
<<<<<<< HEAD
            logger.error("Erreur findById Medecin: {}", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Medecin> findBySpecialite(String specialite) {
        List<Medecin> medecins = new ArrayList<>();
        String sql = "SELECT * FROM MEDECIN WHERE specialite = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, specialite);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                medecins.add(mapResultSetToMedecin(rs));
            }
            return medecins;
        } catch (SQLException e) {
            logger.error("Erreur findBySpecialite", e);
            throw new RuntimeException("Erreur findBySpecialite", e);
        }
    }
=======
            logger.error("Erreur findById Medecin", e);
        }
        return null;
    }
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
    
    @Override
    public List<Medecin> findAll() {
        List<Medecin> medecins = new ArrayList<>();
<<<<<<< HEAD
        String sql = "SELECT * FROM MEDECIN ORDER BY nom, prenom";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
=======
        String sql = "SELECT * FROM MEDECIN";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
            while (rs.next()) {
                medecins.add(mapResultSetToMedecin(rs));
            }
        } catch (SQLException e) {
<<<<<<< HEAD
            logger.error("Erreur findAll Medecin: {}", e.getMessage(), e);
=======
            logger.error("Erreur findAll Medecin", e);
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
        }
        return medecins;
    }

    @Override
    public void update(Medecin medecin) {
<<<<<<< HEAD
        String sql = "UPDATE MEDECIN SET numero_ordre = ?, nom = ?, prenom = ?, " +
                     "specialite = ?, telephone = ?, email = ?, date_embauche = ?, " +
                     "id_departement = ? WHERE id_medecin = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, medecin.getNumeroOrdre());
            ps.setString(2, medecin.getNom());
            ps.setString(3, medecin.getPrenom());
            ps.setString(4, medecin.getSpecialite());
            ps.setString(5, medecin.getTelephone());
            ps.setString(6, medecin.getEmail());
            ps.setDate(7, Date.valueOf(medecin.getDateEmbauche()));
            
            if (medecin.getDepartement() != null && medecin.getDepartement().getId() != null) {
                ps.setLong(8, medecin.getDepartement().getId());
            } else {
                ps.setNull(8, Types.NUMERIC);
            }
            
            ps.setLong(9, medecin.getId());
            
            ps.executeUpdate();
            logger.info("Médecin mis à jour : ID {}", medecin.getId());
        } catch (SQLException e) {
            logger.error("Erreur update Medecin: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la mise à jour du médecin", e);
=======
        String sql = "UPDATE MEDECIN SET NOM=?, PRENOM=?, SPECIALITE=?, EMAIL=?, TELEPHONE=? WHERE ID=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, medecin.getNom());
            ps.setString(2, medecin.getPrenom());
            ps.setString(3, medecin.getSpecialite());
            ps.setString(4, medecin.getEmail());
            ps.setString(5, medecin.getTelephone());
            ps.setLong(6, medecin.getId());
            
            ps.executeUpdate();
            logger.info("Médecin mis à jour ID: {}", medecin.getId());
        } catch (SQLException e) {
            logger.error("Erreur update Medecin", e);
            throw new RuntimeException(e);
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
        }
    }

    @Override
    public void delete(Long id) {
<<<<<<< HEAD
        String sql = "DELETE FROM MEDECIN WHERE id_medecin = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
            logger.info("Médecin supprimé : ID {}", id);
        } catch (SQLException e) {
            logger.error("Erreur delete Medecin: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la suppression du médecin", e);
        }
    }

    private Medecin mapResultSetToMedecin(ResultSet rs) throws SQLException {
        Medecin.MedecinBuilder builder = Medecin.builder()
                .id(rs.getLong("id_medecin"))
                .numeroOrdre(rs.getString("numero_ordre"))
                .nom(rs.getString("nom"))
                .prenom(rs.getString("prenom"))
                .specialite(rs.getString("specialite"))
                .email(rs.getString("email"))
                .telephone(rs.getString("telephone"))
                .dateEmbauche(rs.getDate("date_embauche").toLocalDate());
        
        // Département peut être null
        Long departementId = rs.getLong("id_departement");
        if (!rs.wasNull() && departementId != null && departementId > 0) {
            Departement dept = new Departement();
            dept.setId(departementId);
            builder.departement(dept);
        }
        
        return builder.build();
=======
        String sql = "DELETE FROM MEDECIN WHERE ID=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setLong(1, id);
            ps.executeUpdate();
            logger.info("Médecin supprimé ID: {}", id);
        } catch (SQLException e) {
            logger.error("Erreur delete Medecin", e);
            throw new RuntimeException(e);
        }
    }

    // Méthode utilitaire pour éviter la duplication de code
    private Medecin mapResultSetToMedecin(ResultSet rs) throws SQLException {
        return Medecin.builder()
                .id(rs.getLong("ID"))
                .nom(rs.getString("NOM"))
                .prenom(rs.getString("PRENOM"))
                .specialite(rs.getString("SPECIALITE"))
                .email(rs.getString("EMAIL"))
                .telephone(rs.getString("TELEPHONE"))
                .build();
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
    }
}