package org.example.view;

import javax.swing.*;
import java.awt.*;

public class PatientRegistrationDialog extends JDialog {
    public JTextField txtFirstName = new JTextField();
    public JTextField txtLastName = new JTextField();
    public JComboBox<String> comboGender = new JComboBox<>(new String[]{"Male", "Female", "Other"});
    public JTextField txtPhone = new JTextField();
    public JTextField txtEmail = new JTextField();
    public JTextField txtDOB = new JTextField();
    public JTextField txtBloodType = new JTextField();
    public JTextField txtAddress = new JTextField();
    public JTextField txtEmergencyContact = new JTextField();

    public JButton btnSave = new JButton("Register Patient");
    public JButton btnCancel = new JButton("❌ Cancel");
    private boolean confirmed = false;

    public PatientRegistrationDialog(JFrame parent) {
        super(parent, "Register New Patient", true);
        setSize(450, 550);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        formPanel.add(new JLabel("First Name:*"));
        formPanel.add(txtFirstName);

        formPanel.add(new JLabel("Last Name:*"));
        formPanel.add(txtLastName);

        formPanel.add(new JLabel("Gender:"));
        formPanel.add(comboGender);

        formPanel.add(new JLabel("Phone:*"));
        formPanel.add(txtPhone);

        formPanel.add(new JLabel("Email:"));
        formPanel.add(txtEmail);

        formPanel.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
        formPanel.add(txtDOB);

        formPanel.add(new JLabel("Blood Type:"));
        formPanel.add(txtBloodType);

        formPanel.add(new JLabel("Address:"));
        formPanel.add(txtAddress);

        formPanel.add(new JLabel("Emergency Contact:"));
        formPanel.add(txtEmergencyContact);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnSave.setBackground(new Color(46, 204, 113));
        btnSave.setForeground(Color.WHITE);
        btnCancel.setBackground(new Color(231, 76, 60));
        btnCancel.setForeground(Color.WHITE);

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> { confirmed = true; dispose(); });
        btnCancel.addActionListener(e -> { confirmed = false; dispose(); });
    }

    public boolean isConfirmed() { return confirmed; }
}