package org.example.view;

import org.example.model.Doctor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DoctorDashboard extends JFrame {
    private Doctor currentDoctor;

    // UI Components
    public JButton btnViewAppointments;
    public JButton btnAddDiagnosis;
    public JButton btnCreatePrescription;
    public JButton btnRequestLabTest;
    public JButton btnLogout;

    private JTable appointmentsTable;
    private DefaultTableModel appointmentsModel;
    private JTextField txtAppointmentId;
    private JTextArea taDiagnosis;
    private JTextArea taNotes;

    public DoctorDashboard(Doctor doctor) {
        this.currentDoctor = doctor;

        setTitle("Hospital Management System - Doctor Dashboard");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        headerPanel.setPreferredSize(new Dimension(1100, 80));

        JLabel lblWelcome = new JLabel("Welcome, Dr. " + doctor.getFullName());
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblWelcome.setForeground(Color.WHITE);
        headerPanel.add(lblWelcome);

        JLabel lblSpecialization = new JLabel(doctor.getSpecialization() + " | " + doctor.getDepartment());
        lblSpecialization.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblSpecialization.setForeground(Color.WHITE);
        headerPanel.add(lblSpecialization);

        add(headerPanel, BorderLayout.NORTH);

        // Tabbed Pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Appointments Tab
        tabbedPane.addTab("My Appointments", createAppointmentsPanel());

        // Diagnosis Tab
        tabbedPane.addTab("Add Diagnosis", createDiagnosisPanel());

        // Prescription Tab
        tabbedPane.addTab("Create Prescription", createPrescriptionPanel());

        // Lab Tests Tab
        tabbedPane.addTab("Request Lab Test", createLabTestPanel());

        add(tabbedPane, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(200, 200, 200));
        footerPanel.setPreferredSize(new Dimension(1100, 40));

        btnLogout = new JButton("Logout");
        btnLogout.setBackground(new Color(220, 60, 50));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 12));
        footerPanel.add(btnLogout);

        add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createAppointmentsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnViewAppointments = new JButton("Refresh");
        buttonPanel.add(btnViewAppointments);
        panel.add(buttonPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Patient", "Date", "Time", "Type", "Status", "Diagnosis"};
        appointmentsModel = new DefaultTableModel(columns, 0);
        appointmentsTable = new JTable(appointmentsModel);
        appointmentsTable.setRowHeight(30);
        appointmentsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(appointmentsTable);
        scrollPane.setPreferredSize(new Dimension(1050, 500));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createDiagnosisPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Appointment ID:"), gbc);
        gbc.gridx = 1;
        txtAppointmentId = new JTextField(10);
        panel.add(txtAppointmentId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Diagnosis:"), gbc);
        gbc.gridx = 1;
        taDiagnosis = new JTextArea(5, 30);
        JScrollPane diagScroll = new JScrollPane(taDiagnosis);
        panel.add(diagScroll, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Notes:"), gbc);
        gbc.gridx = 1;
        taNotes = new JTextArea(3, 30);
        JScrollPane notesScroll = new JScrollPane(taNotes);
        panel.add(notesScroll, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        btnAddDiagnosis = new JButton("Save Diagnosis");
        btnAddDiagnosis.setBackground(new Color(46, 204, 113));
        btnAddDiagnosis.setForeground(Color.WHITE);
        btnAddDiagnosis.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(btnAddDiagnosis, gbc);

        return panel;
    }

    private JPanel createPrescriptionPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel infoLabel = new JLabel("Select an appointment from the Appointments tab first, then click below:");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(infoLabel, gbc);

        gbc.gridy = 1;
        btnCreatePrescription = new JButton("Create Prescription");
        btnCreatePrescription.setBackground(new Color(52, 152, 219));
        btnCreatePrescription.setForeground(Color.WHITE);
        btnCreatePrescription.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCreatePrescription.setPreferredSize(new Dimension(250, 50));
        panel.add(btnCreatePrescription, gbc);

        return panel;
    }

    private JPanel createLabTestPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel infoLabel = new JLabel("Select an appointment from the Appointments tab first, then request a lab test:");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(infoLabel, gbc);

        gbc.gridy = 1;
        btnRequestLabTest = new JButton("Request Lab Test");
        btnRequestLabTest.setBackground(new Color(155, 89, 182));
        btnRequestLabTest.setForeground(Color.WHITE);
        btnRequestLabTest.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRequestLabTest.setPreferredSize(new Dimension(250, 50));
        panel.add(btnRequestLabTest, gbc);

        return panel;
    }

    // Getters for Controller
    public DefaultTableModel getAppointmentsTableModel() { return appointmentsModel; }
    public JTable getAppointmentsTable() { return appointmentsTable; }
    public JTextField getTxtAppointmentId() { return txtAppointmentId; }
    public JTextArea getTaDiagnosis() { return taDiagnosis; }
    public JTextArea getTaNotes() { return taNotes; }
    public Doctor getCurrentDoctor() { return currentDoctor; }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void showError(String error) {
        JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public int showConfirmDialog(String message) {
        return JOptionPane.showConfirmDialog(this, message, "Confirm", JOptionPane.YES_NO_OPTION);
    }
}