package ma.ensa.healthcare.dao.interfaces;

import ma.ensa.healthcare.model.Medecin;
import java.util.List;

public interface IMedecinDAO {
    Medecin save(Medecin medecin);
    Medecin findById(Long id);
<<<<<<< HEAD
    List<Medecin> findBySpecialite(String specialite);
=======
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
    List<Medecin> findAll();
    void update(Medecin medecin);
    void delete(Long id);
}