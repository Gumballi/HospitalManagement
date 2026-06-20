package org.example.view;

import javax.swing.*;
import java.awt.*;

public class RegistrationDialog extends JDialog {
    public JTextField txtUsername = new JTextField();
    public JPasswordField txtPassword = new JPasswordField();
    public JTextField txtFirstName = new JTextField();
    public JTextField txtLastName = new JTextField();
    public JTextField txtEmail = new JTextField();
    public JTextField txtPhone = new JTextField();
    public JComboBox<String> comboGender = new JComboBox<>(new String[]{"Male", "Female", "Other"});

    public JLabel lblExtra1 = new JLabel("Specialization:");
    public JTextField txtExtra1 = new JTextField();
    public JLabel lblExtra2 = new JLabel("Department:");
    public JTextField txtExtra2 = new JTextField();

    public JButton btnSave = new JButton("Save User");
    public JButton btnCancel = new JButton("❌ Cancel");
    private boolean confirmed = false;

    public RegistrationDialog(JFrame parent, String role) {
        super(parent, "Register New " + role, true);
        setSize(450, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        formPanel.add(new JLabel("Username:*"));
        formPanel.add(txtUsername);

        formPanel.add(new JLabel("Password:*"));
        formPanel.add(txtPassword);

        formPanel.add(new JLabel("First Name:*"));
        formPanel.add(txtFirstName);

        formPanel.add(new JLabel("Last Name:*"));
        formPanel.add(txtLastName);

        formPanel.add(new JLabel("Email:*"));
        formPanel.add(txtEmail);

        formPanel.add(new JLabel("Phone:*"));
        formPanel.add(txtPhone);

        formPanel.add(new JLabel("Gender:"));
        formPanel.add(comboGender);

        if (role.equals("DOCTOR")) {
            lblExtra1.setText("Specialization:*");
            lblExtra2.setText("Department:*");
            txtExtra1.setVisible(true);
            txtExtra2.setVisible(true);
        } else if (role.equals("RECEPTIONIST")) {
            lblExtra1.setVisible(false);
            txtExtra1.setVisible(false);
            lblExtra2.setVisible(false);
            txtExtra2.setVisible(false);
        }

        formPanel.add(lblExtra1);
        formPanel.add(txtExtra1);
        formPanel.add(lblExtra2);
        formPanel.add(txtExtra2);

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