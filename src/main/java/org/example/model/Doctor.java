package org.example.model;

public class Doctor extends User {
    private int doctorId;
    private String specialization;
    private String department;

    public Doctor() {
        super();
    }

    public Doctor(int userId, String username, String password, String firstName, String lastName,
                  String email, String phone, String gender, int doctorId, String specialization, String department) {
        super(userId, username, password, firstName, lastName, email, phone, gender);
        this.doctorId = doctorId;
        this.specialization = specialization;
        this.department = department;
    }

    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
        this.setUserId(doctorId);
    }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    // Business Methods
    public void diagnosePatient(int patientId, String diagnosis) {
        System.out.println("Doctor " + getFullName() + " diagnosed patient ID: " + patientId);
        System.out.println("   Diagnosis: " + diagnosis);
    }

    public void prescribeMedicine(int patientId, int medicineId, String dosage, String frequency) {
        System.out.println("Doctor " + getFullName() + " prescribed medicine to patient ID: " + patientId);
        System.out.println("   Medicine ID: " + medicineId + " | Dosage: " + dosage + " | Frequency: " + frequency);
    }

    public void requestLabTest(int patientId, String testType) {
        System.out.println("Lab test requested by Dr. " + getLastName());
        System.out.println("   Patient ID: " + patientId + " | Test: " + testType);
    }

    public void viewAppointments() {
        System.out.println("Viewing appointments for Dr. " + getFullName());
    }

    @Override
    public boolean login(String username, String password) {
        return this.getUsername() != null && this.getUsername().equals(username) &&
                this.getPassword() != null && this.getPassword().equals(password);
    }

    @Override
    public String displayInfo() {
        return "Dr. " + getFullName() + " (ID: " + doctorId + ") - " + specialization;
    }

    @Override
    public String getRole() {
        return "DOCTOR";
    }
}