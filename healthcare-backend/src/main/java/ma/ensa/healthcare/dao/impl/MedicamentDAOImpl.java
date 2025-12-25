package ma.ensa.healthcare.dao.impl;

import ma.ensa.healthcare.config.DatabaseConfig;
import ma.ensa.healthcare.dao.interfaces.IMedicamentDAO;
import ma.ensa.healthcare.model.Medicament;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

<<<<<<< HEAD
import java.math.BigDecimal;
=======
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
/**
 * Implémentation DAO pour l'entité MEDICAMENT
 * VERSION MINIMALE - Seulement les méthodes essentielles
 */
=======
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
public class MedicamentDAOImpl implements IMedicamentDAO {
    private static final Logger logger = LoggerFactory.getLogger(MedicamentDAOImpl.class);

    @Override
    public Medicament save(Medicament m) {
<<<<<<< HEAD
        String sql = "INSERT INTO MEDICAMENT (id_medicament, nom_commercial, principe_actif, " +
                     "forme, dosage, prix_unitaire, stock_disponible, stock_alerte) " +
                     "VALUES (seq_medicament.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id_medicament"})) {
            
            ps.setString(1, m.getNomCommercial());
            ps.setString(2, m.getPrincipeActif());
            ps.setString(3, m.getForme());
            ps.setString(4, m.getDosage());
            ps.setBigDecimal(5, m.getPrixUnitaire());
            ps.setInt(6, m.getStockDisponible());
            ps.setInt(7, m.getStockAlerte());
            
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    m.setId(rs.getLong(1));
                }
            }
            
            logger.info("Médicament enregistré : {} (ID: {})", m.getNomCommercial(), m.getId());
        } catch (SQLException e) {
            logger.error("Erreur save Medicament: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la sauvegarde du médicament", e);
=======
        String sql = "INSERT INTO MEDICAMENT (NOM, DOSAGE, INSTRUCTIONS) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"ID"})) {
            ps.setString(1, m.getNom());
            ps.setString(2, m.getDosage());
            ps.setString(3, m.getInstructions());
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) m.setId(rs.getLong(1));
            }
            logger.info("Medicament enregistré : {}", m.getNom());
        } catch (SQLException e) {
            logger.error("Erreur save Medicament", e);
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
        }
        return m;
    }

    @Override
<<<<<<< HEAD
    public Medicament findById(Long id) {
        String sql = "SELECT * FROM MEDICAMENT WHERE id_medicament = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMedicament(rs);
                }
            }
        } catch (SQLException e) {
            logger.error("Erreur findById Medicament: {}", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Medicament> findAll() {
        List<Medicament> list = new ArrayList<>();
        String sql = "SELECT * FROM MEDICAMENT ORDER BY nom_commercial";
=======
    public List<Medicament> findAll() {
        List<Medicament> list = new ArrayList<>();
        String sql = "SELECT * FROM MEDICAMENT";
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
<<<<<<< HEAD
                list.add(mapResultSetToMedicament(rs));
            }
        } catch (SQLException e) {
            logger.error("Erreur findAll Medicament: {}", e.getMessage(), e);
=======
                list.add(new Medicament(
                    rs.getLong("ID"),
                    rs.getString("NOM"),
                    rs.getString("DOSAGE"),
                    rs.getString("INSTRUCTIONS")
                ));
            }
        } catch (SQLException e) {
            logger.error("Erreur findAll Medicament", e);
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
        }
        return list;
    }

    @Override
<<<<<<< HEAD
    public void update(Medicament m) {
        String sql = "UPDATE MEDICAMENT SET nom_commercial = ?, principe_actif = ?, " +
                     "forme = ?, dosage = ?, prix_unitaire = ?, stock_disponible = ?, " +
                     "stock_alerte = ? WHERE id_medicament = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getNomCommercial());
            ps.setString(2, m.getPrincipeActif());
            ps.setString(3, m.getForme());
            ps.setString(4, m.getDosage());
            ps.setBigDecimal(5, m.getPrixUnitaire());
            ps.setInt(6, m.getStockDisponible());
            ps.setInt(7, m.getStockAlerte());
            ps.setLong(8, m.getId());
            
            ps.executeUpdate();
            logger.info("Médicament mis à jour : ID {}", m.getId());
        } catch (SQLException e) {
            logger.error("Erreur update Medicament: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la mise à jour du médicament", e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM MEDICAMENT WHERE id_medicament = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
            logger.info("Médicament supprimé : ID {}", id);
        } catch (SQLException e) {
            logger.error("Erreur delete Medicament: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la suppression du médicament", e);
        }
    }

    @Override
    public List<Medicament> findByNom(String nom) {
        List<Medicament> list = new ArrayList<>();
        String sql = "SELECT * FROM MEDICAMENT WHERE nom_commercial LIKE ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + nom + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToMedicament(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Erreur findByNom: {}", e.getMessage(), e);
        }
        return list;
    }

    private Medicament mapResultSetToMedicament(ResultSet rs) throws SQLException {
        return Medicament.builder()
                .id(rs.getLong("id_medicament"))
                .nomCommercial(rs.getString("nom_commercial"))
                .principeActif(rs.getString("principe_actif"))
                .forme(rs.getString("forme"))
                .dosage(rs.getString("dosage"))
                .prixUnitaire(rs.getBigDecimal("prix_unitaire"))
                .stockDisponible(rs.getInt("stock_disponible"))
                .stockAlerte(rs.getInt("stock_alerte"))
                .build();
    }
=======
    public Medicament findById(Long id) {
        String sql = "SELECT * FROM MEDICAMENT WHERE ID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Medicament(rs.getLong("ID"), rs.getString("NOM"), 
                                      rs.getString("DOSAGE"), rs.getString("INSTRUCTIONS"));
            }
        } catch (SQLException e) { logger.error("Erreur findById Medicament", e); }
        return null;
    }

    @Override public void update(Medicament m) { /* Implementation UPDATE SQL */ }
    @Override public void delete(Long id) { /* Implementation DELETE SQL */ }
    @Override public List<Medicament> findByNom(String nom) { return new ArrayList<>(); }
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
}