package org.example.dao;

import org.example.model.Doctor;
import java.util.List;

public interface DoctorDAO {
    boolean addDoctor(Doctor doctor);
    boolean updateDoctor(Doctor doctor);
    boolean deleteDoctor(int doctorId);
    Doctor getDoctorById(int doctorId);
    Doctor getDoctorByEmail(String email);
    Doctor getDoctorByUsername(String username);
    List<Doctor> getAllDoctors();
    List<Doctor> getDoctorsBySpecialization(String specialization);
    List<Doctor> getDoctorsByDepartment(String department);
}