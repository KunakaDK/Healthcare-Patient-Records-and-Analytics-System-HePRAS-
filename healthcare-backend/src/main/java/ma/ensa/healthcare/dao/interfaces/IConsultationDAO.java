package ma.ensa.healthcare.dao.interfaces;

import ma.ensa.healthcare.model.Consultation;
import java.util.List;

public interface IConsultationDAO {
    Consultation save(Consultation consultation);
    Consultation findById(Long id);
<<<<<<< HEAD
    List<Consultation> findByPatientId(Long patientId);
    List<Consultation> findByMedecinId(Long medecinId);
=======
>>>>>>> 51509288808383cb3589bbc4e9010c3e90972737
    List<Consultation> findAll();
    void update(Consultation consultation);
    void delete(Long id);
    Consultation findByRendezVousId(Long rdvId);
}