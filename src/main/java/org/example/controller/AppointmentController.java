package org.example.controller;

import org.example.view.AppointmentPage;
import org.example.database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class AppointmentController {
    private AppointmentPage view;

    public AppointmentController(AppointmentPage view) {
        this.view = view;
        initController();
        loadAppointments();
    }

    private void initController() {
        view.btnAdd.addActionListener(e -> addAppointment());
        view.btnUpdate.addActionListener(e -> updateAppointment());
        view.btnDelete.addActionListener(e -> deleteAppointment());
        view.btnSearch.addActionListener(e -> searchAppointment());
    }

    private void loadAppointments() {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        String sql = "SELECT * FROM appointments ORDER BY appointment_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("appointment_id"),
                        rs.getInt("patient_id"),
                        rs.getInt("doctor_id"),
                        rs.getDate("appointment_date"),
                        rs.getString("appointment_time"),
                        rs.getString("visit_type"),
                        rs.getString("status")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            view.showError("Error loading appointments: " + e.getMessage());
        }
    }

    private void addAppointment() {
        String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, visit_type, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(view.getTxtPatientId().getText().trim()));
            ps.setInt(2, Integer.parseInt(view.getTxtDoctorId().getText().trim()));
            ps.setDate(3, Date.valueOf(view.getTxtDate().getText().trim()));
            ps.setString(4, view.getTxtTime().getText().trim());
            ps.setString(5, view.getTxtVisitType().getText().trim());
            ps.setString(6, view.getTxtStatus().getText().trim());

            if (ps.executeUpdate() > 0) {
                view.showMessage("Appointment added successfully!");
                loadAppointments();
                clearFields();
            } else {
                view.showError("Failed to add appointment!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            view.showError("Database error: " + e.getMessage());
        }
    }

    private void updateAppointment() {
        String sql = "UPDATE appointments SET patient_id=?, doctor_id=?, appointment_date=?, appointment_time=?, visit_type=?, status=? WHERE appointment_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(view.getTxtPatientId().getText().trim()));
            ps.setInt(2, Integer.parseInt(view.getTxtDoctorId().getText().trim()));
            ps.setDate(3, Date.valueOf(view.getTxtDate().getText().trim()));
            ps.setString(4, view.getTxtTime().getText().trim());
            ps.setString(5, view.getTxtVisitType().getText().trim());
            ps.setString(6, view.getTxtStatus().getText().trim());
            ps.setInt(7, Integer.parseInt(view.getTxtAppointmentId().getText().trim()));

            if (ps.executeUpdate() > 0) {
                view.showMessage("Appointment updated successfully!");
                loadAppointments();
                clearFields();
            } else {
                view.showError("Failed to update appointment!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            view.showError("Database error: " + e.getMessage());
        }
    }

    private void deleteAppointment() {
        int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this appointment?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM appointments WHERE appointment_id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, Integer.parseInt(view.getTxtAppointmentId().getText().trim()));

                if (ps.executeUpdate() > 0) {
                    view.showMessage("Appointment deleted successfully!");
                    loadAppointments();
                    clearFields();
                } else {
                    view.showError("Failed to delete appointment!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                view.showError("Database error: " + e.getMessage());
            }
        }
    }

    private void searchAppointment() {
        String appointmentId = view.getTxtAppointmentId().getText().trim();

        if (appointmentId.isEmpty()) {
            loadAppointments();
            return;
        }

        String sql = "SELECT * FROM appointments WHERE appointment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(appointmentId));
            ResultSet rs = ps.executeQuery();

            DefaultTableModel model = view.getTableModel();
            model.setRowCount(0);

            if (rs.next()) {
                Object[] row = {
                        rs.getInt("appointment_id"),
                        rs.getInt("patient_id"),
                        rs.getInt("doctor_id"),
                        rs.getDate("appointment_date"),
                        rs.getString("appointment_time"),
                        rs.getString("visit_type"),
                        rs.getString("status")
                };
                model.addRow(row);

                // Populate form fields
                view.getTxtPatientId().setText(String.valueOf(rs.getInt("patient_id")));
                view.getTxtDoctorId().setText(String.valueOf(rs.getInt("doctor_id")));
                view.getTxtDate().setText(String.valueOf(rs.getDate("appointment_date")));
                view.getTxtTime().setText(rs.getString("appointment_time"));
                view.getTxtVisitType().setText(rs.getString("visit_type"));
                view.getTxtStatus().setText(rs.getString("status"));
            } else {
                view.showError("Appointment not found!");
                loadAppointments();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            view.showError("Database error: " + e.getMessage());
        }
    }

    private void clearFields() {
        view.getTxtAppointmentId().setText("");
        view.getTxtPatientId().setText("");
        view.getTxtDoctorId().setText("");
        view.getTxtDate().setText("");
        view.getTxtTime().setText("");
        view.getTxtVisitType().setText("");
        view.getTxtStatus().setText("");
    }
}