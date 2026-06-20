package org.example.dao;

import org.example.model.Prescription;
import java.util.List;

public interface PrescriptionDAO {
    boolean addPrescription(Prescription prescription);
    boolean updatePrescription(Prescription prescription);
    boolean deletePrescription(int prescriptionId);
    Prescription getPrescriptionById(int prescriptionId);
    List<Prescription> getPrescriptionsByPatientId(int patientId);
    List<Prescription> getPrescriptionsByDoctorId(int doctorId);
    List<Prescription> getAllPrescriptions();
}