package ma.ensa.healthcare.dao.interfaces;

import ma.ensa.healthcare.model.Patient;
import java.util.List;

public interface IPatientDAO {
    Patient save(Patient patient);
    Patient findById(Long id);
<<<<<<< HEAD
    Patient findByCin(String cin);
=======
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
    List<Patient> findAll();
    void update(Patient patient);
    void delete(Long id);
    List<Patient> findByNom(String nom);
}