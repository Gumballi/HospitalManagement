package org.example.model;

import java.util.Date;

public class Appointment {
    private int appointmentId;
    private int patientId;
    private int doctorId;
    private Date appointmentDate;
    private String appointmentTime;
    private String diagnosis;
    private String visitType;
    private String status;
    private String note;

    // Object references
    private Patient patient;
    private Doctor doctor;
    private Bill bill;

    public Appointment() {}

    // Business Methods
    public void scheduleAppointment(Patient patient, Doctor doctor, Date date, String time, String visitType) {
        this.patient = patient;
        this.doctor = doctor;
        this.patientId = patient.getPatientId();
        this.doctorId = doctor.getDoctorId();
        this.appointmentDate = date;
        this.appointmentTime = time;
        this.visitType = visitType;
        this.status = "SCHEDULED";
        System.out.println("Appointment scheduled for patient " + patient.getFullName() + " with Dr. " + doctor.getLastName());
    }

    public void updateAppointment(Date newDate, String newTime, String newVisitType) {
        this.appointmentDate = newDate;
        this.appointmentTime = newTime;
        this.visitType = newVisitType;
        System.out.println("Appointment ID: " + appointmentId + " updated successfully.");
    }

    public void cancelAppointment() {
        this.status = "CANCELLED";
        System.out.println("Appointment ID: " + appointmentId + " has been cancelled.");
    }

    // Getters and Setters
    public int getAppointmentId() { return appointmentId; }
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }

    public Date getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(Date appointmentDate) { this.appointmentDate = appointmentDate; }

    public String getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(String appointmentTime) { this.appointmentTime = appointmentTime; }

    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    public String getVisitType() { return visitType; }
    public void setVisitType(String visitType) { this.visitType = visitType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    public Bill getBill() { return bill; }
    public void setBill(Bill bill) { this.bill = bill; }

    public boolean isCompleted() { return "COMPLETED".equals(status); }
    public boolean isCancelled() { return "CANCELLED".equals(status); }
}