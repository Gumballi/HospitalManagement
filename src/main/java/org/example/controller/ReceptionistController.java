package org.example.controller;

import org.example.view.ReceptionistPage;
import org.example.view.PatientRegistrationDialog;
import org.example.view.AppointmentPage;
import org.example.view.PatientDirectoryPage;
import org.example.view.BillingPage;
import org.example.model.Receptionist;
import org.example.model.Patient;
import org.example.database.DatabaseConnection;

import javax.swing.*;
import java.sql.*;

public class ReceptionistController {
    private ReceptionistPage view;
    private Receptionist currentReceptionist;

    public ReceptionistController(ReceptionistPage view) {
        this.view = view;
        this.currentReceptionist = view.getCurrentReceptionist();
        initController();
    }

    private void initController() {
        view.btnRegisterPatient.addActionListener(e -> registerPatient());
        view.btnScheduleAppt.addActionListener(e -> openAppointmentPage());
        view.btnDirectory.addActionListener(e -> openPatientDirectory());
        view.btnBilling.addActionListener(e -> openBillingPage());
        view.btnLogout.addActionListener(e -> logout());
    }

    private void registerPatient() {
        PatientRegistrationDialog dialog = new PatientRegistrationDialog(view);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            String sql = "INSERT INTO patients (first_name, last_name, gender, phone, email, date_of_birth, blood_type, address, emergency_contact) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, dialog.txtFirstName.getText().trim());
                ps.setString(2, dialog.txtLastName.getText().trim());
                ps.setString(3, (String) dialog.comboGender.getSelectedItem());
                ps.setString(4, dialog.txtPhone.getText().trim());
                ps.setString(5, dialog.txtEmail.getText().trim());
                ps.setDate(6, !dialog.txtDOB.getText().trim().isEmpty() ? Date.valueOf(dialog.txtDOB.getText().trim()) : null);
                ps.setString(7, dialog.txtBloodType.getText().trim());
                ps.setString(8, dialog.txtAddress.getText().trim());
                ps.setString(9, dialog.txtEmergencyContact.getText().trim());

                if (ps.executeUpdate() > 0) {
                    ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        int patientId = rs.getInt(1);

                        // Create Patient object and call business method
                        Patient newPatient = new Patient();
                        newPatient.setFirstName(dialog.txtFirstName.getText().trim());
                        newPatient.setLastName(dialog.txtLastName.getText().trim());
                        newPatient.setPatientId(patientId);

                        //Call UML Business Method
                        currentReceptionist.registerPatient(newPatient);

                        view.showMessage("Patient registered successfully! ID: " + patientId);
                    }
                } else {
                    view.showError("Registration failed!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                view.showError("Database error: " + ex.getMessage());
            }
        }
    }

    private void openAppointmentPage() {
        AppointmentPage appointmentPage = new AppointmentPage();
        new AppointmentController(appointmentPage);
        appointmentPage.setVisible(true);
    }

    private void openPatientDirectory() {
        PatientDirectoryPage directoryPage = new PatientDirectoryPage();
        new PatientDirectoryController(directoryPage);
        directoryPage.setVisible(true);
    }

    private void openBillingPage() {
        BillingPage billingPage = new BillingPage();
        new BillingController(billingPage);
        billingPage.setVisible(true);
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