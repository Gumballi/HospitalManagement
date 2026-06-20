package org.example.dao;

import org.example.model.Prescription;
import org.example.model.PrescriptionItem;
import org.example.model.Patient;
import org.example.model.Doctor;
import org.example.database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionDAOImp implements PrescriptionDAO {

    private PatientDAO patientDAO = new PatientDAOImp();
    private DoctorDAO doctorDAO = new DoctorDAOImp();
    private PrescriptionItemDAO prescriptionItemDAO = new PrescriptionItemDAOImp();

    @Override
    public boolean addPrescription(Prescription prescription) {
        String sql = "INSERT INTO prescriptions (doctor_id, patient_id, prescription_date) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, prescription.getDoctorId());
            ps.setInt(2, prescription.getPatientId());
            ps.setDate(3, new java.sql.Date(prescription.getPrescriptionDate().getTime()));

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    prescription.setPrescriptionId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updatePrescription(Prescription prescription) {
        String sql = "UPDATE prescriptions SET doctor_id=?, patient_id=?, prescription_date=? WHERE prescription_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, prescription.getDoctorId());
            ps.setInt(2, prescription.getPatientId());
            ps.setDate(3, new java.sql.Date(prescription.getPrescriptionDate().getTime()));
            ps.setInt(4, prescription.getPrescriptionId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deletePrescription(int prescriptionId) {
        String sql = "DELETE FROM prescriptions WHERE prescription_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, prescriptionId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Prescription getPrescriptionById(int prescriptionId) {
        String sql = "SELECT * FROM prescriptions WHERE prescription_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, prescriptionId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Prescription prescription = new Prescription();
                prescription.setPrescriptionId(rs.getInt("prescription_id"));
                prescription.setDoctorId(rs.getInt("doctor_id"));
                prescription.setPatientId(rs.getInt("patient_id"));
                prescription.setPrescriptionDate(rs.getDate("prescription_date"));

                // Load patient and doctor objects
                Patient patient = patientDAO.getPatientById(prescription.getPatientId());
                Doctor doctor = doctorDAO.getDoctorById(prescription.getDoctorId());
                prescription.setPatient(patient);
                prescription.setDoctor(doctor);

                // Load prescription items
                List<PrescriptionItem> items = prescriptionItemDAO.getItemsByPrescriptionId(prescriptionId);
                prescription.setItems(items);

                return prescription;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Prescription> getPrescriptionsByPatientId(int patientId) {
        List<Prescription> prescriptions = new ArrayList<>();
        String sql = "SELECT * FROM prescriptions WHERE patient_id = ? ORDER BY prescription_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Prescription prescription = new Prescription();
                prescription.setPrescriptionId(rs.getInt("prescription_id"));
                prescription.setDoctorId(rs.getInt("doctor_id"));
                prescription.setPatientId(rs.getInt("patient_id"));
                prescription.setPrescriptionDate(rs.getDate("prescription_date"));

                Doctor doctor = doctorDAO.getDoctorById(prescription.getDoctorId());
                prescription.setDoctor(doctor);

                List<PrescriptionItem> items = prescriptionItemDAO.getItemsByPrescriptionId(prescription.getPrescriptionId());
                prescription.setItems(items);

                prescriptions.add(prescription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prescriptions;
    }

    @Override
    public List<Prescription> getPrescriptionsByDoctorId(int doctorId) {
        List<Prescription> prescriptions = new ArrayList<>();
        String sql = "SELECT * FROM prescriptions WHERE doctor_id = ? ORDER BY prescription_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Prescription prescription = new Prescription();
                prescription.setPrescriptionId(rs.getInt("prescription_id"));
                prescription.setDoctorId(rs.getInt("doctor_id"));
                prescription.setPatientId(rs.getInt("patient_id"));
                prescription.setPrescriptionDate(rs.getDate("prescription_date"));

                Patient patient = patientDAO.getPatientById(prescription.getPatientId());
                prescription.setPatient(patient);

                List<PrescriptionItem> items = prescriptionItemDAO.getItemsByPrescriptionId(prescription.getPrescriptionId());
                prescription.setItems(items);

                prescriptions.add(prescription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prescriptions;
    }

    @Override
    public List<Prescription> getAllPrescriptions() {
        List<Prescription> prescriptions = new ArrayList<>();
        String sql = "SELECT * FROM prescriptions ORDER BY prescription_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Prescription prescription = new Prescription();
                prescription.setPrescriptionId(rs.getInt("prescription_id"));
                prescription.setDoctorId(rs.getInt("doctor_id"));
                prescription.setPatientId(rs.getInt("patient_id"));
                prescription.setPrescriptionDate(rs.getDate("prescription_date"));

                List<PrescriptionItem> items = prescriptionItemDAO.getItemsByPrescriptionId(prescription.getPrescriptionId());
                prescription.setItems(items);

                prescriptions.add(prescription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prescriptions;
    }
}