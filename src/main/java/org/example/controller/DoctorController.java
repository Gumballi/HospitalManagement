package org.example.controller;

import org.example.view.DoctorDashboard;
import org.example.model.Doctor;
import org.example.model.Appointment;
import org.example.dao.AppointmentDAO;
import org.example.dao.AppointmentDAOImp;
import org.example.dao.PrescriptionDAO;
import org.example.dao.PrescriptionDAOImp;
import org.example.database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.List;

public class DoctorController {
    private DoctorDashboard view;
    private Doctor currentDoctor;
    private AppointmentDAO appointmentDAO;
    private PrescriptionDAO prescriptionDAO;

    public DoctorController(DoctorDashboard view) {
        this.view = view;
        this.currentDoctor = view.getCurrentDoctor();
        this.appointmentDAO = new AppointmentDAOImp();
        this.prescriptionDAO = new PrescriptionDAOImp();
        initController();
        loadAppointments();
    }

    private void initController() {
        view.btnViewAppointments.addActionListener(e -> loadAppointments());
        view.btnAddDiagnosis.addActionListener(e -> addDiagnosis());
        view.btnCreatePrescription.addActionListener(e -> createPrescription());
        view.btnRequestLabTest.addActionListener(e -> requestLabTest());
        view.btnLogout.addActionListener(e -> logout());
    }

    private void loadAppointments() {
        DefaultTableModel model = view.getAppointmentsTableModel();
        model.setRowCount(0);

        List<Appointment> appointments = appointmentDAO.getAppointmentsByDoctorId(currentDoctor.getDoctorId());

        for (Appointment apt : appointments) {
            // Get patient name
            String patientName = "Unknown";
            String sql = "SELECT first_name, last_name FROM patients WHERE patient_id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, apt.getPatientId());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    patientName = rs.getString("first_name") + " " + rs.getString("last_name");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Object[] row = {
                    apt.getAppointmentId(),
                    patientName,
                    apt.getAppointmentDate(),
                    apt.getAppointmentTime(),
                    apt.getVisitType(),
                    apt.getStatus(),
                    apt.getDiagnosis() != null ? apt.getDiagnosis() : "Pending"
            };
            model.addRow(row);
        }

        //Call UML Business Method
        currentDoctor.viewAppointments();
    }

    private void addDiagnosis() {
        int selectedRow = view.getAppointmentsTable().getSelectedRow();
        if (selectedRow == -1) {
            view.showError("Please select an appointment first!");
            return;
        }

        int appointmentId = (int) view.getAppointmentsTableModel().getValueAt(selectedRow, 0);
        String patientIdStr = view.getTxtAppointmentId().getText();

        Appointment appointment = appointmentDAO.getAppointmentById(appointmentId);

        String diagnosis = view.getTaDiagnosis().getText().trim();
        String notes = view.getTaNotes().getText().trim();

        if (diagnosis.isEmpty()) {
            view.showError("Please enter a diagnosis!");
            return;
        }

        String sql = "UPDATE appointments SET diagnosis = ?, note = ?, status = 'COMPLETED' WHERE appointment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, diagnosis);
            ps.setString(2, notes);
            ps.setInt(3, appointmentId);

            if (ps.executeUpdate() > 0) {
                //Call UML Business Method
                currentDoctor.diagnosePatient(appointment.getPatientId(), diagnosis);

                view.showMessage("Diagnosis added successfully!");
                loadAppointments();
                view.getTaDiagnosis().setText("");
                view.getTaNotes().setText("");
                view.getTxtAppointmentId().setText("");
            } else {
                view.showError("Failed to add diagnosis!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            view.showError("Database error: " + e.getMessage());
        }
    }

    private void createPrescription() {
        int selectedRow = view.getAppointmentsTable().getSelectedRow();
        if (selectedRow == -1) {
            view.showError("Please select an appointment first!");
            return;
        }

        int appointmentId = (int) view.getAppointmentsTableModel().getValueAt(selectedRow, 0);
        Appointment appointment = appointmentDAO.getAppointmentById(appointmentId);

        if (appointment == null) {
            view.showError("Appointment not found!");
            return;
        }

        String medicineName = JOptionPane.showInputDialog(view, "Enter Medicine Name:");
        if (medicineName == null || medicineName.trim().isEmpty()) return;

        String dosage = JOptionPane.showInputDialog(view, "Enter Dosage (e.g., 500mg):");
        String frequency = JOptionPane.showInputDialog(view, "Enter Frequency (e.g., Twice daily):");
        String quantityStr = JOptionPane.showInputDialog(view, "Enter Quantity:");

        if (dosage == null || frequency == null || quantityStr == null) return;

        try {
            int quantity = Integer.parseInt(quantityStr.trim());

            // Get medicine ID
            int medicineId = 0;
            String getMedSql = "SELECT medicine_id FROM medicines WHERE medicine_name = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(getMedSql)) {
                ps.setString(1, medicineName);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    medicineId = rs.getInt("medicine_id");
                }
            }

            // Insert prescription
            String sql = "INSERT INTO prescriptions (doctor_id, patient_id, prescription_date) VALUES (?, ?, GETDATE())";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, currentDoctor.getDoctorId());
                ps.setInt(2, appointment.getPatientId());

                if (ps.executeUpdate() > 0) {
                    ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        int prescriptionId = rs.getInt(1);

                        // Insert prescription item
                        String itemSql = "INSERT INTO prescription_items (prescription_id, medicine_id, quantity_prescribed, dosage, frequency) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement ps2 = conn.prepareStatement(itemSql)) {
                            ps2.setInt(1, prescriptionId);
                            ps2.setInt(2, medicineId);
                            ps2.setInt(3, quantity);
                            ps2.setString(4, dosage);
                            ps2.setString(5, frequency);
                            ps2.executeUpdate();
                        }

                        //Call UML Business Method
                        currentDoctor.prescribeMedicine(appointment.getPatientId(), medicineId, dosage, frequency);

                        view.showMessage("Prescription created successfully!");
                    }
                }
            }
        } catch (NumberFormatException e) {
            view.showError("Invalid quantity!");
        } catch (SQLException e) {
            e.printStackTrace();
            view.showError("Database error: " + e.getMessage());
        }
    }

    private void requestLabTest() {
        int selectedRow = view.getAppointmentsTable().getSelectedRow();
        if (selectedRow == -1) {
            view.showError("Please select an appointment first!");
            return;
        }

        int appointmentId = (int) view.getAppointmentsTableModel().getValueAt(selectedRow, 0);
        Appointment appointment = appointmentDAO.getAppointmentById(appointmentId);

        String[] labTests = {"Blood Test", "X-Ray", "MRI", "CT Scan", "Urine Test", "ECG", "Ultrasound"};
        String testType = (String) JOptionPane.showInputDialog(view, "Select Lab Test:", "Lab Test Request",
                JOptionPane.QUESTION_MESSAGE, null, labTests, labTests[0]);

        if (testType != null && !testType.trim().isEmpty()) {
            //Call UML Business Method
            currentDoctor.requestLabTest(appointment.getPatientId(), testType);

            view.showMessage("Lab test '" + testType + "' requested successfully!");
        }
    }

    private void logout() {
        int confirm = view.showConfirmDialog("Are you sure you want to logout?");
        if (confirm == JOptionPane.YES_OPTION) {
            view.dispose();
            org.example.view.LoginFrame loginFrame = new org.example.view.LoginFrame();
            new LoginController(loginFrame);
            loginFrame.setVisible(true);
        }
    }
}