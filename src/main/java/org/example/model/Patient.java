package org.example.model;

import java.util.Date;
import java.util.List;

public class Patient extends User {
    private int patientId;
    private Date dateOfBirth;
    private String bloodType;
    private String address;
    private String emergencyContact;
    private List<Appointment> appointments;
    private List<Prescription> prescriptions;

    public Patient() {
        super();
    }

    public Patient(int patientId, String firstName, String lastName, String gender, String phone,
                   String email, Date dateOfBirth, String bloodType, String address, String emergencyContact) {
        super(patientId, null, null, firstName, lastName, email, phone, gender);
        this.patientId = patientId;
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
        this.address = address;
        this.emergencyContact = emergencyContact;
    }

    public Patient(int userId, String username, String password, String firstName, String lastName,
                   String email, String phone, String gender, int patientId, Date dateOfBirth,
                   String bloodType, String address, String emergencyContact) {
        super(userId, username, password, firstName, lastName, email, phone, gender);
        this.patientId = patientId;
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
        this.address = address;
        this.emergencyContact = emergencyContact;
    }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) {
        this.patientId = patientId;
        this.setUserId(patientId);
    }

    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getBloodType() { return bloodType; }
    public void setBloodType(String bloodType) { this.bloodType = bloodType; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }

    public List<Appointment> getAppointments() { return appointments; }
    public void setAppointments(List<Appointment> appointments) { this.appointments = appointments; }

    public List<Prescription> getPrescriptions() { return prescriptions; }
    public void setPrescriptions(List<Prescription> prescriptions) { this.prescriptions = prescriptions; }

    // Business Methods
    public void registerPatient() {
        System.out.println("Patient registered: " + getFullName());
    }

    public void updatePatient(String newPhone, String newAddress) {
        setPhone(newPhone);
        this.address = newAddress;
        System.out.println("Patient information updated for: " + getFullName());
    }

    public String viewPatientInfo() {
        return "═══════════════════════════════════\n" +
                "          PATIENT INFORMATION\n" +
                "═══════════════════════════════════\n" +
                "Patient ID: " + patientId + "\n" +
                "Name: " + getFullName() + "\n" +
                "Gender: " + getGender() + "\n" +
                "Date of Birth: " + dateOfBirth + "\n" +
                "Blood Type: " + bloodType + "\n" +
                "Phone: " + getPhone() + "\n" +
                "Email: " + getEmail() + "\n" +
                "Address: " + address + "\n" +
                "Emergency Contact: " + emergencyContact + "\n";
    }

    @Override
    public boolean login(String username, String password) {
        return this.getEmail() != null && this.getEmail().equals(username);
    }

    @Override
    public String displayInfo() {
        return "Patient: " + getFullName() + " (ID: " + patientId + ") - Blood Type: " + bloodType;
    }

    @Override
    public String getRole() {
        return "PATIENT";
    }
}