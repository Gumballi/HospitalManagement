package org.example.dao;

import org.example.model.Patient;
import java.util.List;

public interface PatientDAO {
    boolean addPatient(Patient patient);
    boolean updatePatient(Patient patient);
    boolean deletePatient(int patientId);
    Patient getPatientById(int patientId);
    Patient getPatientByEmail(String email);
    List<Patient> getAllPatients();
    List<Patient> searchPatientsByName(String name);
}