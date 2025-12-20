package ma.ensa.healthcare.dao.interfaces;

import ma.ensa.healthcare.model.Patient;
import java.util.List;

public interface IPatientDAO {
    Patient save(Patient patient);
    Patient update(Patient patient);
    void delete(Long id);
    Patient findById(Long id);
    List<Patient> findAll();
    List<Patient> findByNom(String nom);
}