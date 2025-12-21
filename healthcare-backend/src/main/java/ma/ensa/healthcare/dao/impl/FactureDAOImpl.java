package ma.ensa.healthcare.dao.impl;

import ma.ensa.healthcare.config.DatabaseConfig;
import ma.ensa.healthcare.dao.interfaces.IFactureDAO;
import ma.ensa.healthcare.model.Facture;
import ma.ensa.healthcare.model.enums.StatutPaiement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FactureDAOImpl implements IFactureDAO {
    private static final Logger logger = LoggerFactory.getLogger(FactureDAOImpl.class);

    @Override
    public Facture save(Facture f) {
        String sql = "INSERT INTO FACTURE (MONTANT, DATE_FACTURE, STATUT, CONSULTATION_ID) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"ID"})) {
            ps.setDouble(1, f.getMontant());
            ps.setDate(2, Date.valueOf(f.getDateFacture()));
            ps.setString(3, f.getStatut().name());
            ps.setLong(4, f.getConsultation().getId());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) f.setId(rs.getLong(1));
        } catch (SQLException e) { logger.error("Erreur save Facture", e); }
        return f;
    }

    @Override
    public List<Facture> findAll() {
        List<Facture> list = new ArrayList<>();
        String sql = "SELECT * FROM FACTURE";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(Facture.builder()
                        .id(rs.getLong("ID"))
                        .montant(rs.getDouble("MONTANT"))
                        .dateFacture(rs.getDate("DATE_FACTURE").toLocalDate())
                        .statut(StatutPaiement.valueOf(rs.getString("STATUT")))
                        .build());
            }
        } catch (SQLException e) { logger.error("Erreur findAll Facture", e); }
        return list;
    }

    @Override public Facture findById(Long id) { return null; } // À implémenter si besoin
    @Override public void updateStatut(Long id, String statut) {
        String sql = "UPDATE FACTURE SET STATUT = ? WHERE ID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, statut);
            ps.setLong(2, id);
            ps.executeUpdate();
        } catch (SQLException e) { logger.error("Erreur updateStatut", e); }
    }
}