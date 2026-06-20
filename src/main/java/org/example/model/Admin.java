package org.example.model;

public class Admin extends User {
    private int adminId;

    public Admin() {
        super();
    }

    public Admin(int userId, String username, String password, String firstName, String lastName,
                 String email, String phone, String gender, int adminId) {
        super(userId, username, password, firstName, lastName, email, phone, gender);
        this.adminId = adminId;
    }

    public int getAdminId() { return adminId; }
    public void setAdminId(int adminId) {
        this.adminId = adminId;
        this.setUserId(adminId);
    }

    // Business Methods
    public void addDoctor(Doctor doctor) {
        System.out.println("Admin " + getFullName() + " added doctor: Dr. " + doctor.getLastName());
    }

    public void updateDoctor(int doctorId, Doctor updatedDoctor) {
        System.out.println("Admin " + getFullName() + " updated doctor ID: " + doctorId);
    }

    public void deleteDoctor(int doctorId) {
        System.out.println("Admin " + getFullName() + " deleted doctor ID: " + doctorId);
    }

    public void manageMedicine(Medicine medicine, String action) {
        System.out.println("Admin " + getFullName() + " - " + action + " medicine: " + medicine.getMedicineName());
    }

    public void generateReports() {
        System.out.println("Admin " + getFullName() + " generated system reports");
    }

    @Override
    public boolean login(String username, String password) {
        return this.getUsername() != null && this.getUsername().equals(username) &&
                this.getPassword() != null && this.getPassword().equals(password);
    }

    @Override
    public String displayInfo() {
        return "Admin: " + getFullName() + " (ID: " + adminId + ")";
    }

    @Override
    public String getRole() {
        return "ADMIN";
    }
}