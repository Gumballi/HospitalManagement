package org.example.controller;

import org.example.view.PatientDashboard;
import org.example.model.Patient;
import org.example.model.Appointment;
import org.example.model.Prescription;
import org.example.model.Bill;
import org.example.dao.AppointmentDAO;
import org.example.dao.AppointmentDAOImp;
import org.example.dao.PrescriptionDAO;
import org.example.dao.PrescriptionDAOImp;
import org.example.dao.BillDAO;
import org.example.dao.BillDAOImp;
import org.example.database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.List;

public class PatientController {
    private PatientDashboard view;
    private Patient currentPatient;
    private AppointmentDAO appointmentDAO;
    private PrescriptionDAO prescriptionDAO;
    private BillDAO billDAO;

    public PatientController(PatientDashboard view, Patient patient) {
        this.view = view;
        this.currentPatient = patient;
        this.appointmentDAO = new AppointmentDAOImp();
        this.prescriptionDAO = new PrescriptionDAOImp();
        this.billDAO = new BillDAOImp();
        initController();
        loadAppointments();
        loadPrescriptions();
        loadBills();
    }

    private void initController() {
        view.btnViewAppointments.addActionListener(e -> loadAppointments());
        view.btnViewPrescriptions.addActionListener(e -> loadPrescriptions());
        view.btnViewBills.addActionListener(e -> loadBills());
        view.btnUpdateProfile.addActionListener(e -> updateProfile());
        view.btnLogout.addActionListener(e -> logout());
    }

    private void loadAppointments() {
        DefaultTableModel model = view.getAppointmentsTableModel();
        model.setRowCount(0);

        List<Appointment> appointments = appointmentDAO.getAppointmentsByPatientId(currentPatient.getPatientId());

        for (Appointment apt : appointments) {
            // Get doctor name
            String doctorName = "Unknown";
            String sql = "SELECT first_name, last_name FROM doctors WHERE doctor_id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, apt.getDoctorId());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    doctorName = "Dr. " + rs.getString("first_name") + " " + rs.getString("last_name");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Object[] row = {
                    apt.getAppointmentId(),
                    doctorName,
                    apt.getAppointmentDate(),
                    apt.getAppointmentTime(),
                    apt.getVisitType(),
                    apt.getStatus()
            };
            model.addRow(row);
        }
    }

    private void loadPrescriptions() {
        DefaultTableModel model = view.getPrescriptionsTableModel();
        model.setRowCount(0);

        List<Prescription> prescriptions = prescriptionDAO.getPrescriptionsByPatientId(currentPatient.getPatientId());

        for (Prescription p : prescriptions) {
            // Get doctor name
            String doctorName = "Unknown";
            String sql = "SELECT first_name, last_name FROM doctors WHERE doctor_id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, p.getDoctorId());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    doctorName = "Dr. " + rs.getString("first_name") + " " + rs.getString("last_name");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Object[] row = {
                    p.getPrescriptionId(),
                    doctorName,
                    p.getPrescriptionDate(),
                    p.getItems().size() + " medicines"
            };
            model.addRow(row);
        }
    }

    private void loadBills() {
        DefaultTableModel model = view.getBillsTableModel();
        model.setRowCount(0);

        List<Bill> bills = billDAO.getBillsByPatientId(currentPatient.getPatientId());

        for (Bill bill : bills) {
            Object[] row = {
                    bill.getBillId(),
                    bill.getBillDate(),
                    "ETB " + String.format("%,.2f", bill.getTotalAmount()),
                    bill.getPaymentStatus(),
                    bill.getPaymentMethod() != null ? bill.getPaymentMethod() : "-"
            };
            model.addRow(row);
        }
    }

    private void updateProfile() {
        JTextField txtPhone = new JTextField(currentPatient.getPhone(), 15);
        JTextField txtAddress = new JTextField(currentPatient.getAddress(), 20);
        JTextField txtEmail = new JTextField(currentPatient.getEmail(), 20);
        JTextField txtEmergencyContact = new JTextField(currentPatient.getEmergencyContact(), 15);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel("Phone:"));
        panel.add(txtPhone);
        panel.add(new JLabel("Address:"));
        panel.add(txtAddress);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("Emergency Contact:"));
        panel.add(txtEmergencyContact);

        int result = JOptionPane.showConfirmDialog(view, panel, "Update Profile",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String sql = "UPDATE patients SET phone = ?, address = ?, email = ?, emergency_contact = ? WHERE patient_id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, txtPhone.getText());
                ps.setString(2, txtAddress.getText());
                ps.setString(3, txtEmail.getText());
                ps.setString(4, txtEmergencyContact.getText());
                ps.setInt(5, currentPatient.getPatientId());

                if (ps.executeUpdate() > 0) {
                    //Call UML Business Method
                    currentPatient.updatePatient(txtPhone.getText(), txtAddress.getText());
                    currentPatient.setEmail(txtEmail.getText());
                    currentPatient.setEmergencyContact(txtEmergencyContact.getText());

                    view.showMessage("Profile updated successfully!");
                } else {
                    view.showError("Update failed!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                view.showError("Database error: " + e.getMessage());
            }
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