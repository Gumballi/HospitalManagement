package org.example.model;

public class Receptionist extends User {
    private int receptionistId;

    public Receptionist() {
        super();
    }

    public Receptionist(int userId, String username, String password, String firstName, String lastName,
                        String email, String phone, String gender, int receptionistId) {
        super(userId, username, password, firstName, lastName, email, phone, gender);
        this.receptionistId = receptionistId;
    }

    public int getReceptionistId() { return receptionistId; }
    public void setReceptionistId(int receptionistId) {
        this.receptionistId = receptionistId;
        this.setUserId(receptionistId);
    }

    // Business Methods
    public void registerPatient(Patient patient) {
        System.out.println("Receptionist " + getFullName() + " registered patient: " + patient.getFullName());
    }

    public void scheduleAppointment(int patientId, int doctorId, String date, String time) {
        System.out.println("Appointment scheduled by receptionist " + getFullName());
        System.out.println("   Patient ID: " + patientId + " | Doctor ID: " + doctorId);
        System.out.println("   Date: " + date + " | Time: " + time);
    }

    public void cancelAppointment(int appointmentId) {
        System.out.println("❌ Appointment " + appointmentId + " cancelled by receptionist " + getFullName());
    }

    @Override
    public boolean login(String username, String password) {
        return this.getUsername() != null && this.getUsername().equals(username) &&
                this.getPassword() != null && this.getPassword().equals(password);
    }

    @Override
    public String displayInfo() {
        return "Receptionist: " + getFullName() + " (ID: " + receptionistId + ")";
    }

    @Override
    public String getRole() {
        return "RECEPTIONIST";
    }
}