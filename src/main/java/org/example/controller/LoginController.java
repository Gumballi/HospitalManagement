package org.example.controller;

import org.example.view.LoginFrame;
import org.example.model.User;
import org.example.model.Admin;
import org.example.model.Doctor;
import org.example.model.Patient;
import org.example.model.Receptionist;
import org.example.view.AdminPage;
import org.example.view.DoctorDashboard;
import org.example.view.PatientDashboard;
import org.example.view.ReceptionistPage;
import org.example.database.DatabaseConnection;

import javax.swing.*;
import java.sql.*;

public class LoginController {
    private LoginFrame view;

    public LoginController(LoginFrame view) {
        this.view = view;
        initController();
    }

    private void initController() {
        view.btnLogin.addActionListener(e -> handleLogin());
        view.btnCancel.addActionListener(e -> System.exit(0));
    }

    private void handleLogin() {
        String username = view.txtUsername.getText().trim();
        String password = new String(view.txtPassword.getPassword()).trim();
        String role = (String) view.cmbRole.getSelectedItem();

        if (username.isEmpty()) {
            view.showError("Please enter username/email!");
            return;
        }

        User user = authenticate(username, password, role);

        if (user != null) {
            view.showMessage("Welcome back, " + user.getFullName() + "!");
            view.dispose();
            launchDashboard(user);
        } else {
            view.showError("Invalid credentials!");
        }
    }

    private User authenticate(String username, String password, String role) {
        try (Connection conn = DatabaseConnection.getConnection()) {

            switch (role) {
                case "Admin":
                    // Admin login from users table
                    String adminSql = "SELECT username, password, role FROM users WHERE username = ? AND password = ? AND role = 'ADMIN'";
                    try (PreparedStatement ps = conn.prepareStatement(adminSql)) {
                        ps.setString(1, username);
                        ps.setString(2, password);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            return new Admin(
                                    1,  // adminId
                                    rs.getString("username"),
                                    rs.getString("password"),
                                    "System",
                                    "Admin",
                                    "admin@hospital.et",
                                    "+251911000000",
                                    "Male",
                                    1001
                            );
                        }
                    }
                    break;

                case "Doctor":
                    // Doctor login - check users table first, then get details from doctors table
                    String doctorSql = "SELECT u.username, u.password, d.doctor_id, d.first_name, d.last_name, " +
                            "d.email, d.phone, d.gender, d.specialization, d.department " +
                            "FROM users u " +
                            "JOIN doctors d ON u.username = d.username " +
                            "WHERE u.username = ? AND u.password = ? AND u.role = 'DOCTOR'";
                    try (PreparedStatement ps = conn.prepareStatement(doctorSql)) {
                        ps.setString(1, username);
                        ps.setString(2, password);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            return new Doctor(
                                    rs.getInt("doctor_id"),
                                    rs.getString("username"),
                                    rs.getString("password"),
                                    rs.getString("first_name"),
                                    rs.getString("last_name"),
                                    rs.getString("email"),
                                    rs.getString("phone"),
                                    rs.getString("gender"),
                                    rs.getInt("doctor_id"),
                                    rs.getString("specialization"),
                                    rs.getString("department")
                            );
                        }
                    }
                    break;

                case "Receptionist":
                    // Receptionist login
                    String receptionistSql = "SELECT u.username, u.password, r.receptionist_id, r.first_name, r.last_name, " +
                            "r.email, r.phone, r.gender " +
                            "FROM users u " +
                            "JOIN receptionists r ON u.username = r.username " +
                            "WHERE u.username = ? AND u.password = ? AND u.role = 'RECEPTIONIST'";
                    try (PreparedStatement ps = conn.prepareStatement(receptionistSql)) {
                        ps.setString(1, username);
                        ps.setString(2, password);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            return new Receptionist(
                                    rs.getInt("receptionist_id"),
                                    rs.getString("username"),
                                    rs.getString("password"),
                                    rs.getString("first_name"),
                                    rs.getString("last_name"),
                                    rs.getString("email"),
                                    rs.getString("phone"),
                                    rs.getString("gender"),
                                    rs.getInt("receptionist_id")
                            );
                        }
                    }
                    break;

                case "Patient":
                    // Patient login - no password, just email
                    String patientSql = "SELECT patient_id, first_name, last_name, gender, phone, email, " +
                            "date_of_birth, blood_type, address, emergency_contact " +
                            "FROM patients WHERE email = ?";
                    try (PreparedStatement ps = conn.prepareStatement(patientSql)) {
                        ps.setString(1, username);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            return new Patient(
                                    rs.getInt("patient_id"),
                                    rs.getString("first_name"),
                                    rs.getString("last_name"),
                                    rs.getString("gender"),
                                    rs.getString("phone"),
                                    rs.getString("email"),
                                    rs.getDate("date_of_birth"),
                                    rs.getString("blood_type"),
                                    rs.getString("address"),
                                    rs.getString("emergency_contact")
                            );
                        }
                    }
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            view.showError("Database error: " + e.getMessage());
        }
        return null;
    }

    private void launchDashboard(User user) {
        SwingUtilities.invokeLater(() -> {
            if (user instanceof Admin) {
                AdminPage adminPage = new AdminPage((Admin) user);
                new AdminController(adminPage);
                adminPage.setVisible(true);
            } else if (user instanceof Doctor) {
                DoctorDashboard doctorDashboard = new DoctorDashboard((Doctor) user);
                new DoctorController(doctorDashboard);
                doctorDashboard.setVisible(true);
            } else if (user instanceof Patient) {
                PatientDashboard patientDashboard = new PatientDashboard((Patient) user);
                new PatientController(patientDashboard, (Patient) user);
                patientDashboard.setVisible(true);
            } else if (user instanceof Receptionist) {
                ReceptionistPage receptionistPage = new ReceptionistPage((Receptionist) user);
                new ReceptionistController(receptionistPage);
                receptionistPage.setVisible(true);
            }
        });
    }
}