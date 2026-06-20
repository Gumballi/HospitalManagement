package org.example.view;

import org.example.model.Receptionist;
import javax.swing.*;
import java.awt.*;

public class ReceptionistPage extends JFrame {
    private Receptionist currentReceptionist;

    public JButton btnRegisterPatient = new JButton("Register New Patient");
    public JButton btnScheduleAppt = new JButton("Schedule Appointment");
    public JButton btnDirectory = new JButton("Patient Directory");
    public JButton btnBilling = new JButton("Billing & Invoices");
    public JButton btnLogout = new JButton("Logout");

    public ReceptionistPage(Receptionist receptionist) {
        this.currentReceptionist = receptionist;

        setTitle("Ethiopian Hospital Management System - Front Desk Portal");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 248, 255));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        headerPanel.setPreferredSize(new Dimension(700, 80));

        JLabel welcomeLabel = new JLabel("Reception Portal: Welcome, " + receptionist.getFullName());
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        welcomeLabel.setForeground(Color.WHITE);
        headerPanel.add(welcomeLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Button Grid
        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        gridPanel.setBackground(Color.WHITE);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Dimension buttonSize = new Dimension(200, 80);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

        btnRegisterPatient.setPreferredSize(buttonSize);
        btnRegisterPatient.setFont(buttonFont);
        btnRegisterPatient.setBackground(new Color(46, 204, 113));
        btnRegisterPatient.setForeground(Color.WHITE);

        btnScheduleAppt.setPreferredSize(buttonSize);
        btnScheduleAppt.setFont(buttonFont);
        btnScheduleAppt.setBackground(new Color(52, 152, 219));
        btnScheduleAppt.setForeground(Color.WHITE);

        btnDirectory.setPreferredSize(buttonSize);
        btnDirectory.setFont(buttonFont);
        btnDirectory.setBackground(new Color(155, 89, 182));
        btnDirectory.setForeground(Color.WHITE);

        btnBilling.setPreferredSize(buttonSize);
        btnBilling.setFont(buttonFont);
        btnBilling.setBackground(new Color(241, 196, 15));
        btnBilling.setForeground(Color.WHITE);

        gridPanel.add(btnRegisterPatient);
        gridPanel.add(btnScheduleAppt);
        gridPanel.add(btnDirectory);
        gridPanel.add(btnBilling);

        mainPanel.add(gridPanel, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(new Color(240, 248, 255));
        btnLogout.setBackground(new Color(231, 76, 60));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 12));
        footerPanel.add(btnLogout);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    public Receptionist getCurrentReceptionist() { return currentReceptionist; }

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