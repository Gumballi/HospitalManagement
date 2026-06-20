package org.example.controller;

import org.example.view.AdminPage;
import org.example.view.RegistrationDialog;
import org.example.model.Admin;
import org.example.model.Doctor;
import org.example.dao.DoctorDAO;
import org.example.dao.DoctorDAOImp;
import org.example.dao.MedicineDAO;
import org.example.dao.MedicineDAOImp;
import org.example.database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdminController {
    private AdminPage view;
    private Admin currentAdmin;
    private DoctorDAO doctorDAO;
    private MedicineDAO medicineDAO;
    private MedicineController medicineController;

    public AdminController(AdminPage view) {
        this.view = view;
        this.currentAdmin = view.getCurrentAdmin();
        this.doctorDAO = new DoctorDAOImp();
        this.medicineDAO = new MedicineDAOImp();
        this.medicineController = new MedicineController(view);
        initController();
    }

    private void initController() {
        // Staff Management
        view.btnManageDoctors.addActionListener(e -> handleDoctorManagement());
        view.btnManageReceptionists.addActionListener(e -> handleReceptionistManagement());

        // Medicine Management
        view.btnAddMedicine.addActionListener(e -> medicineController.addMedicine());
        view.btnViewMedicines.addActionListener(e -> medicineController.viewMedicines());
        view.btnUpdateStock.addActionListener(e -> medicineController.updateStock());
        view.btnLowStockAlert.addActionListener(e -> medicineController.checkLowStock());

        // Reports
        view.btnViewReports.addActionListener(e -> generateReports());
        view.btnSettings.addActionListener(e -> showSettings());

        // Logout
        view.btnLogout.addActionListener(e -> logout());
    }

    private void handleDoctorManagement() {
        String[] options = {"Register New Doctor", "Update Doctor", "Delete Doctor"};
        int choice = JOptionPane.showOptionDialog(view, "Select Doctor Management Task:", "Doctor Management",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            registerDoctor();
        } else if (choice == 1) {
            updateDoctor();
        } else if (choice == 2) {
            deleteDoctor();
        }
    }

    private void handleReceptionistManagement() {
        String[] options = {"Register New Receptionist", "Update Receptionist", "Delete Receptionist"};
        int choice = JOptionPane.showOptionDialog(view, "Select Receptionist Management Task:", "Receptionist Management",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            registerReceptionist();
        } else if (choice == 1) {
            updateReceptionist();
        } else if (choice == 2) {
            deleteReceptionist();
        }
    }

    private void registerDoctor() {
        RegistrationDialog dialog = new RegistrationDialog(view, "DOCTOR");
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            String sql = "INSERT INTO doctors (first_name, last_name, gender, phone, email, specialization, department, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, dialog.txtFirstName.getText().trim());
                ps.setString(2, dialog.txtLastName.getText().trim());
                ps.setString(3, (String) dialog.comboGender.getSelectedItem());
                ps.setString(4, dialog.txtPhone.getText().trim());
                ps.setString(5, dialog.txtEmail.getText().trim());
                ps.setString(6, dialog.txtExtra1.getText().trim());
                ps.setString(7, dialog.txtExtra2.getText().trim());
                ps.setString(8, dialog.txtUsername.getText().trim());
                ps.setString(9, new String(dialog.txtPassword.getPassword()).trim());

                if (ps.executeUpdate() > 0) {
                    Doctor newDoctor = new Doctor();
                    newDoctor.setFirstName(dialog.txtFirstName.getText().trim());
                    newDoctor.setLastName(dialog.txtLastName.getText().trim());
                    newDoctor.setSpecialization(dialog.txtExtra1.getText().trim());

                    //Call UML Business Method
                    currentAdmin.addDoctor(newDoctor);

                    view.showMessage("Doctor registered successfully!");
                } else {
                    view.showError("Registration failed!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                view.showError("Database error: " + ex.getMessage());
            }
        }
    }

    private void registerReceptionist() {
        RegistrationDialog dialog = new RegistrationDialog(view, "RECEPTIONIST");
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            String sql = "INSERT INTO receptionists (first_name, last_name, gender, phone, email, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, dialog.txtFirstName.getText().trim());
                ps.setString(2, dialog.txtLastName.getText().trim());
                ps.setString(3, (String) dialog.comboGender.getSelectedItem());
                ps.setString(4, dialog.txtPhone.getText().trim());
                ps.setString(5, dialog.txtEmail.getText().trim());
                ps.setString(6, dialog.txtUsername.getText().trim());
                ps.setString(7, new String(dialog.txtPassword.getPassword()).trim());

                if (ps.executeUpdate() > 0) {
                    view.showMessage("Receptionist registered successfully!");
                } else {
                    view.showError("Registration failed!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                view.showError("Database error: " + ex.getMessage());
            }
        }
    }

    private void updateDoctor() {
        String doctorIdStr = JOptionPane.showInputDialog(view, "Enter Doctor ID to update:");
        if (doctorIdStr == null || doctorIdStr.trim().isEmpty()) return;

        int doctorId = Integer.parseInt(doctorIdStr.trim());
        Doctor doctor = doctorDAO.getDoctorById(doctorId);

        if (doctor == null) {
            view.showError("Doctor not found!");
            return;
        }

        String newPhone = JOptionPane.showInputDialog(view, "Enter new phone number:", doctor.getPhone());
        String newEmail = JOptionPane.showInputDialog(view, "Enter new email:", doctor.getEmail());
        String newDepartment = JOptionPane.showInputDialog(view, "Enter new department:", doctor.getDepartment());

        if (newPhone != null && !newPhone.trim().isEmpty()) {
            doctor.setPhone(newPhone);
        }
        if (newEmail != null && !newEmail.trim().isEmpty()) {
            doctor.setEmail(newEmail);
        }
        if (newDepartment != null && !newDepartment.trim().isEmpty()) {
            doctor.setDepartment(newDepartment);
        }

        if (doctorDAO.updateDoctor(doctor)) {
            //Call UML Business Method
            currentAdmin.updateDoctor(doctorId, doctor);
            view.showMessage("Doctor updated successfully!");
        } else {
            view.showError("Update failed!");
        }
    }

    private void updateReceptionist() {
        String recepIdStr = JOptionPane.showInputDialog(view, "Enter Receptionist ID to update:");
        if (recepIdStr == null || recepIdStr.trim().isEmpty()) return;

        int recepId = Integer.parseInt(recepIdStr.trim());

        String newPhone = JOptionPane.showInputDialog(view, "Enter new phone number:");
        String newEmail = JOptionPane.showInputDialog(view, "Enter new email:");

        if (newPhone == null || newEmail == null) return;

        String sql = "UPDATE receptionists SET phone = ?, email = ? WHERE receptionist_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newPhone);
            ps.setString(2, newEmail);
            ps.setInt(3, recepId);

            if (ps.executeUpdate() > 0) {
                view.showMessage("Receptionist updated successfully!");
            } else {
                view.showError("Update failed!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            view.showError("Database error: " + ex.getMessage());
        }
    }

    private void deleteDoctor() {
        String doctorIdStr = JOptionPane.showInputDialog(view, "Enter Doctor ID to delete:");
        if (doctorIdStr == null || doctorIdStr.trim().isEmpty()) return;

        int confirm = view.showConfirmDialog("Are you sure you want to delete this doctor?");
        if (confirm == JOptionPane.YES_OPTION) {
            int doctorId = Integer.parseInt(doctorIdStr.trim());
            if (doctorDAO.deleteDoctor(doctorId)) {
                //Call UML Business Method
                currentAdmin.deleteDoctor(doctorId);
                view.showMessage("Doctor deleted successfully!");
            } else {
                view.showError("Delete failed!");
            }
        }
    }

    private void deleteReceptionist() {
        String recepIdStr = JOptionPane.showInputDialog(view, "Enter Receptionist ID to delete:");
        if (recepIdStr == null || recepIdStr.trim().isEmpty()) return;

        int confirm = view.showConfirmDialog("Are you sure you want to delete this receptionist?");
        if (confirm == JOptionPane.YES_OPTION) {
            int recepId = Integer.parseInt(recepIdStr.trim());
            String sql = "DELETE FROM receptionists WHERE receptionist_id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, recepId);
                if (ps.executeUpdate() > 0) {
                    view.showMessage("Receptionist deleted successfully!");
                } else {
                    view.showError("Delete failed!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                view.showError("Database error: " + ex.getMessage());
            }
        }
    }

    private void generateReports() {
        StringBuilder report = new StringBuilder();
        report.append("HOSPITAL MANAGEMENT SYSTEM REPORT\n");

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Total Patients
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM patients")) {
                if (rs.next()) {
                    report.append("Total Patients: ").append(rs.getInt(1)).append("\n");
                }
            }

            // Total Doctors
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM doctors")) {
                if (rs.next()) {
                    report.append("Total Doctors: ").append(rs.getInt(1)).append("\n");
                }
            }

            // Appointment Statistics
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT status, COUNT(*) FROM appointments GROUP BY status")) {
                report.append("\nAppointment Statistics:\n");
                while (rs.next()) {
                    report.append("   - ").append(rs.getString(1)).append(": ").append(rs.getInt(2)).append("\n");
                }
            }

            // Revenue
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT SUM(total_amount) FROM bills WHERE payment_status = 'PAID'")) {
                if (rs.next()) {
                    double revenue = rs.getDouble(1);
                    report.append("\nTotal Revenue: ETB ").append(String.format("%,.2f", revenue)).append("\n");
                }
            }

            //Call UML Business Method
            currentAdmin.generateReports();

            JTextArea textArea = new JTextArea(report.toString());
            textArea.setEditable(false);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            JOptionPane.showMessageDialog(view, new JScrollPane(textArea), "System Report", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            ex.printStackTrace();
            view.showError("Error generating report: " + ex.getMessage());
        }
    }

    private void showSettings() {
        JOptionPane.showMessageDialog(view, "System Settings\n\nDatabase: SQL Server\nVersion: 1.0\nDeveloped for Ethiopian Hospital Management",
                "Settings", JOptionPane.INFORMATION_MESSAGE);
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