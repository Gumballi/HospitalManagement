package org.example.dao;

import org.example.model.Appointment;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

public interface AppointmentDAO {
    boolean addAppointment(Appointment appointment);
    boolean updateAppointment(Appointment appointment);
    boolean deleteAppointment(int appointmentId);
    boolean cancelAppointment(int appointmentId);
    Appointment getAppointmentById(int appointmentId);
    List<Appointment> getAppointmentsByPatientId(int patientId);
    List<Appointment> getAppointmentsByDoctorId(int doctorId);
    List<Appointment> getAppointmentsByDate(Date date);
    List<Appointment> getAppointmentsByStatus(String status);
    List<Appointment> getAllAppointments();

    ResultSet getPatientAppointmentHistory(int patientId);
    ResultSet generateDailyRevenueReport(Date reportDate);
    ResultSet getDoctorPerformanceReport(Date startDate, Date endDate);
}
