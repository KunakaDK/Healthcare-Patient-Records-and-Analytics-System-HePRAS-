package ma.ensa.healthcare.dao.impl;

import ma.ensa.healthcare.config.DatabaseConfig;
import ma.ensa.healthcare.dao.interfaces.IFactureDAO;
import ma.ensa.healthcare.model.Facture;
<<<<<<< HEAD
import ma.ensa.healthcare.model.Consultation;
import ma.ensa.healthcare.model.Patient;
import ma.ensa.healthcare.model.enums.StatutPaiement;
import ma.ensa.healthcare.model.enums.ModePaiement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation DAO pour l'entité FACTURE
 * Correspondance avec la table Oracle FACTURE
 */
=======
import ma.ensa.healthcare.model.enums.StatutPaiement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
public class FactureDAOImpl implements IFactureDAO {
    private static final Logger logger = LoggerFactory.getLogger(FactureDAOImpl.class);

    @Override
    public Facture save(Facture f) {
<<<<<<< HEAD
        // ✅ AJOUT : Vérification anti-doublon pour id_consultation (UNIQUE)
        if (f.getConsultation() != null && f.getConsultation().getId() != null) {
            Facture existante = findByConsultationId(f.getConsultation().getId());
            if (existante != null) {
                logger.warn("Facture déjà existante pour consultation ID {}, retour facture existante ID {}", 
                           f.getConsultation().getId(), existante.getId());
                return existante;  // ✅ Retourner l'existante au lieu de créer un doublon
            }
        }

        // ✅ Toutes les colonnes de la table FACTURE
        String sql = "INSERT INTO FACTURE (id_facture, numero_facture, id_patient, " +
                     "id_consultation, date_facture, montant_consultation, montant_medicaments, " +
                     "montant_total, montant_paye, statut_paiement, mode_paiement, date_paiement) " +
                     "VALUES (seq_facture.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id_facture"})) {
            
            ps.setString(1, f.getNumeroFacture());
            ps.setLong(2, f.getPatient().getId());
            ps.setLong(3, f.getConsultation().getId());
            ps.setDate(4, Date.valueOf(f.getDateFacture()));
            ps.setBigDecimal(5, f.getMontantConsultation());
            ps.setBigDecimal(6, f.getMontantMedicaments());
            ps.setBigDecimal(7, f.getMontantTotal());
            ps.setBigDecimal(8, f.getMontantPaye());
            ps.setString(9, f.getStatutPaiement().name());
            
            // Mode de paiement peut être null si pas encore payé
            if (f.getModePaiement() != null) {
                ps.setString(10, f.getModePaiement().name());
            } else {
                ps.setNull(10, Types.VARCHAR);
            }
            
            // Date de paiement peut être null
            if (f.getDatePaiement() != null) {
                ps.setDate(11, Date.valueOf(f.getDatePaiement()));
            } else {
                ps.setNull(11, Types.DATE);
            }
            
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        f.setId(rs.getLong(1));
                    }
                }
            }
            
            logger.info("Facture enregistrée avec succès : {} (ID: {})", 
                       f.getNumeroFacture(), f.getId());
        } catch (SQLException e) {
            logger.error("Erreur save Facture: {}", e.getMessage(), e);
=======
        // Check your actual column names in the FACTURE table
        // If MONTANT doesn't exist, it might be MONTANT_TOTAL or another name
        String sql = "INSERT INTO FACTURE (MONTANT_TOTAL, DATE_FACTURE, CONSULTATION_ID) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"ID"})) {
            ps.setDouble(1, f.getMontant());
            ps.setDate(2, Date.valueOf(f.getDateFacture()));
            ps.setLong(3, f.getConsultation().getId());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) f.setId(rs.getLong(1));
        } catch (SQLException e) { 
            logger.error("Erreur save Facture", e); 
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
            throw new RuntimeException("Erreur lors de la sauvegarde de la facture", e);
        }
        return f;
    }

    @Override
<<<<<<< HEAD
    public Facture findById(Long id) {
        String sql = "SELECT * FROM FACTURE WHERE id_facture = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToFacture(rs);
                }
            }
        } catch (SQLException e) {
            logger.error("Erreur findById Facture: {}", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Facture> findAll() {
        List<Facture> list = new ArrayList<>();
        String sql = "SELECT * FROM FACTURE ORDER BY date_facture DESC";
=======
    public List<Facture> findAll() {
        List<Facture> list = new ArrayList<>();
        // Update column names in the SELECT query as well
        String sql = "SELECT * FROM FACTURE";
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
<<<<<<< HEAD
                list.add(mapResultSetToFacture(rs));
            }
        } catch (SQLException e) {
            logger.error("Erreur findAll Facture: {}", e.getMessage(), e);
=======
                // Try different column names based on your table structure
                double montant = 0.0;
                try {
                    montant = rs.getDouble("MONTANT_TOTAL");
                } catch (SQLException e1) {
                    try {
                        montant = rs.getDouble("MONTANT");
                    } catch (SQLException e2) {
                        montant = rs.getDouble("MONTANT_FACTURE");
                    }
                }
                
                Facture.FactureBuilder builder = Facture.builder()
                        .id(rs.getLong("ID"))
                        .montant(montant)
                        .dateFacture(rs.getDate("DATE_FACTURE").toLocalDate());
                
                // Only add statut if the column exists
                try {
                    String statutStr = rs.getString("STATUT");
                    if (statutStr != null) {
                        builder.statut(StatutPaiement.valueOf(statutStr));
                    }
                } catch (SQLException e) {
                    // Column doesn't exist, skip it
                }
                
                list.add(builder.build());
            }
        } catch (SQLException e) { 
            logger.error("Erreur findAll Facture", e); 
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
        }
        return list;
    }

<<<<<<< HEAD
    @Override
    public void update(Facture f) {
        String sql = "UPDATE FACTURE SET montant_paye = ?, statut_paiement = ?, " +
                     "mode_paiement = ?, date_paiement = ? WHERE id_facture = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, f.getMontantPaye());
            ps.setString(2, f.getStatutPaiement().name());
            
            if (f.getModePaiement() != null) {
                ps.setString(3, f.getModePaiement().name());
            } else {
                ps.setNull(3, Types.VARCHAR);
            }
            
            if (f.getDatePaiement() != null) {
                ps.setDate(4, Date.valueOf(f.getDatePaiement()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            
            ps.setLong(5, f.getId());
            
            int rowsAffected = ps.executeUpdate();
            logger.info("Facture mise à jour : {} lignes affectées", rowsAffected);
        } catch (SQLException e) {
            logger.error("Erreur update Facture: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la mise à jour de la facture", e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM FACTURE WHERE id_facture = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            int rowsAffected = ps.executeUpdate();
            logger.info("Facture supprimée : {} lignes affectées", rowsAffected);
        } catch (SQLException e) {
            logger.error("Erreur delete Facture: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la suppression de la facture", e);
        }
    }

    @Override
    public void updateStatut(Long id, String statut) {
        String sql = "UPDATE FACTURE SET statut_paiement = ? WHERE id_facture = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, statut);
            ps.setLong(2, id);
            int rowsAffected = ps.executeUpdate();
            logger.info("Statut facture mis à jour : {} lignes affectées", rowsAffected);
        } catch (SQLException e) {
            logger.error("Erreur updateStatut: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la mise à jour du statut", e);
        }
    }

    @Override
    public void enregistrerPaiement(Long factureId, BigDecimal montant, ModePaiement modePaiement, LocalDate datePaiement) {
        String sql = "UPDATE FACTURE SET montant_paye = montant_paye + ?, " +
                    "statut_paiement = CASE " +
                    "  WHEN montant_paye + ? >= montant_total THEN 'PAYE' " +
                    "  ELSE 'PARTIEL' END, " +
                    "mode_paiement = ?, date_paiement = ? " +
                    "WHERE id_facture = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setBigDecimal(1, montant);
            pstmt.setBigDecimal(2, montant);
            pstmt.setString(3, modePaiement.name());
            pstmt.setDate(4, Date.valueOf(datePaiement));
            pstmt.setLong(5, factureId);
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Erreur enregistrerPaiement", e);
            throw new RuntimeException("Erreur enregistrerPaiement", e);
        }
    }

    @Override
    public List<Facture> findByPatientId(Long patientId) {
        List<Facture> factures = new ArrayList<>();
        String sql = "SELECT * FROM FACTURE WHERE id_patient = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, patientId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                factures.add(mapResultSetToFacture(rs));
            }
            return factures;
        } catch (SQLException e) {
            logger.error("Erreur findByPatientId", e);
            throw new RuntimeException("Erreur findByPatientId", e);
        }
    }

    @Override
    public String genererNumeroFacture() {
        String sql = "SELECT 'FAC-' || TO_CHAR(SYSDATE, 'YYYY') || '-' || " +
                    "LPAD(NVL(MAX(TO_NUMBER(SUBSTR(numero_facture, -4))), 0) + 1, 4, '0') " +
                    "FROM FACTURE " +
                    "WHERE numero_facture LIKE 'FAC-' || TO_CHAR(SYSDATE, 'YYYY') || '-%'";
        
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            return "FAC-" + java.time.Year.now() + "-0001";
        } catch (SQLException e) {
            logger.error("Erreur genererNumeroFacture", e);
            return "FAC-" + java.time.Year.now() + "-0001";
        }
    }

    @Override
    public BigDecimal getTotalImpaye() {
        String sql = "SELECT SUM(montant_total - montant_paye) FROM FACTURE " +
                    "WHERE statut_paiement IN ('EN_ATTENTE', 'PARTIEL')";
        
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                BigDecimal total = rs.getBigDecimal(1);
                return total != null ? total : BigDecimal.ZERO;
            }
            return BigDecimal.ZERO;
        } catch (SQLException e) {
            logger.error("Erreur getTotalImpaye", e);
            return BigDecimal.ZERO;
        }
    }

    @Override
    public List<Facture> findFacturesImpayees() {
        List<Facture> factures = new ArrayList<>();
        String sql = "SELECT * FROM FACTURE " +
                    "WHERE statut_paiement IN ('EN_ATTENTE', 'PARTIEL')";
        
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                factures.add(mapResultSetToFacture(rs));
            }
            return factures;
        } catch (SQLException e) {
            logger.error("Erreur findFacturesImpayees", e);
            throw new RuntimeException("Erreur findFacturesImpayees", e);
        }
    }

    @Override
    public BigDecimal getRevenusPeriode(LocalDate dateDebut, LocalDate dateFin) {
        String sql = "SELECT SUM(montant_paye) FROM FACTURE " +
                    "WHERE date_facture BETWEEN ? AND ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDate(1, Date.valueOf(dateDebut));
            pstmt.setDate(2, Date.valueOf(dateFin));
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                BigDecimal total = rs.getBigDecimal(1);
                return total != null ? total : BigDecimal.ZERO;
            }
            return BigDecimal.ZERO;
        } catch (SQLException e) {
            logger.error("Erreur getRevenusPeriode", e);
            return BigDecimal.ZERO;
        }
    }

    @Override
    public Facture findByNumero(String numeroFacture) {
        String sql = "SELECT * FROM FACTURE WHERE numero_facture = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, numeroFacture);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToFacture(rs);
            }
            return null;
        } catch (SQLException e) {
            logger.error("Erreur findByNumero", e);
            throw new RuntimeException("Erreur findByNumero", e);
        }
    }

    @Override
    public List<Facture> findByStatut(StatutPaiement statut) {
        List<Facture> factures = new ArrayList<>();
        String sql = "SELECT * FROM FACTURE WHERE statut_paiement = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, statut.name());
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                factures.add(mapResultSetToFacture(rs));
            }
            return factures;
        } catch (SQLException e) {
            logger.error("Erreur findByStatut", e);
            throw new RuntimeException("Erreur findByStatut", e);
        }
    }

    @Override
    public Facture findByConsultationId(Long consultationId) {
        String sql = "SELECT * FROM FACTURE WHERE id_consultation = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, consultationId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToFacture(rs);
            }
            return null;
        } catch (SQLException e) {
            logger.error("Erreur findByConsultationId", e);
            return null;  // ✅ Retourner null au lieu de throw (pour la vérification anti-doublon)
        }
    }

    /**
     * Mapper ResultSet vers objet Facture
     */
    private Facture mapResultSetToFacture(ResultSet rs) throws SQLException {
        // Créer des objets minimaux pour les relations
        Patient patient = new Patient();
        patient.setId(rs.getLong("id_patient"));
        
        Consultation consultation = new Consultation();
        consultation.setId(rs.getLong("id_consultation"));
        
        Facture.FactureBuilder builder = Facture.builder()
                .id(rs.getLong("id_facture"))
                .numeroFacture(rs.getString("numero_facture"))
                .patient(patient)
                .consultation(consultation)
                .dateFacture(rs.getDate("date_facture").toLocalDate())
                .montantConsultation(rs.getBigDecimal("montant_consultation"))
                .montantMedicaments(rs.getBigDecimal("montant_medicaments"))
                .montantTotal(rs.getBigDecimal("montant_total"))
                .montantPaye(rs.getBigDecimal("montant_paye"))
                .statutPaiement(StatutPaiement.valueOf(rs.getString("statut_paiement")));
        
        // Mode de paiement peut être null
        String modePaiement = rs.getString("mode_paiement");
        if (modePaiement != null && !rs.wasNull()) {
            builder.modePaiement(ModePaiement.valueOf(modePaiement));
        }
        
        // Date de paiement peut être null
        Date datePaiement = rs.getDate("date_paiement");
        if (datePaiement != null && !rs.wasNull()) {
            builder.datePaiement(datePaiement.toLocalDate());
        }
        
        return builder.build();
=======
    @Override 
    public Facture findById(Long id) { 
        return null; 
    }
    
    @Override 
    public void updateStatut(Long id, String statut) {
        // Check if column exists before trying to update
        try (Connection conn = DatabaseConfig.getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet columns = meta.getColumns(null, null, "FACTURE", "STATUT");
            if (columns.next()) {
                String sql = "UPDATE FACTURE SET STATUT = ? WHERE ID = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, statut);
                    ps.setLong(2, id);
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) { logger.error("Erreur updateStatut", e); }
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
    }
}