package org.example.dao;

import org.example.model.Appointment;
import org.example.model.Patient;
import org.example.model.Doctor;
import org.example.database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppointmentDAOImp implements AppointmentDAO {

    private PatientDAO patientDAO = new PatientDAOImp();
    private DoctorDAO doctorDAO = new DoctorDAOImp();

    @Override
    public boolean addAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, visit_type, status, diagnosis, note) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, appointment.getPatientId());
            ps.setInt(2, appointment.getDoctorId());
            ps.setDate(3, new java.sql.Date(appointment.getAppointmentDate().getTime()));
            ps.setString(4, appointment.getAppointmentTime());
            ps.setString(5, appointment.getVisitType());
            ps.setString(6, appointment.getStatus());
            ps.setString(7, appointment.getDiagnosis());
            ps.setString(8, appointment.getNote());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    appointment.setAppointmentId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateAppointment(Appointment appointment) {
        String sql = "UPDATE appointments SET patient_id=?, doctor_id=?, appointment_date=?, appointment_time=?, visit_type=?, status=?, diagnosis=?, note=? WHERE appointment_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, appointment.getPatientId());
            ps.setInt(2, appointment.getDoctorId());
            ps.setDate(3, new java.sql.Date(appointment.getAppointmentDate().getTime()));
            ps.setString(4, appointment.getAppointmentTime());
            ps.setString(5, appointment.getVisitType());
            ps.setString(6, appointment.getStatus());
            ps.setString(7, appointment.getDiagnosis());
            ps.setString(8, appointment.getNote());
            ps.setInt(9, appointment.getAppointmentId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteAppointment(int appointmentId) {
        String sql = "DELETE FROM appointments WHERE appointment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean cancelAppointment(int appointmentId) {
        String sql = "UPDATE appointments SET status = 'CANCELLED' WHERE appointment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Appointment getAppointmentById(int appointmentId) {
        String sql = "SELECT * FROM appointments WHERE appointment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(rs.getInt("appointment_id"));
                appointment.setPatientId(rs.getInt("patient_id"));
                appointment.setDoctorId(rs.getInt("doctor_id"));
                appointment.setAppointmentDate(rs.getDate("appointment_date"));
                appointment.setAppointmentTime(rs.getString("appointment_time"));
                appointment.setVisitType(rs.getString("visit_type"));
                appointment.setStatus(rs.getString("status"));
                appointment.setDiagnosis(rs.getString("diagnosis"));
                appointment.setNote(rs.getString("note"));

                // Load related objects
                Patient patient = patientDAO.getPatientById(appointment.getPatientId());
                Doctor doctor = doctorDAO.getDoctorById(appointment.getDoctorId());
                appointment.setPatient(patient);
                appointment.setDoctor(doctor);

                return appointment;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Appointment> getAppointmentsByPatientId(int patientId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE patient_id = ? ORDER BY appointment_date DESC, appointment_time DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(rs.getInt("appointment_id"));
                appointment.setPatientId(rs.getInt("patient_id"));
                appointment.setDoctorId(rs.getInt("doctor_id"));
                appointment.setAppointmentDate(rs.getDate("appointment_date"));
                appointment.setAppointmentTime(rs.getString("appointment_time"));
                appointment.setVisitType(rs.getString("visit_type"));
                appointment.setStatus(rs.getString("status"));
                appointment.setDiagnosis(rs.getString("diagnosis"));
                appointment.setNote(rs.getString("note"));

                Doctor doctor = doctorDAO.getDoctorById(appointment.getDoctorId());
                appointment.setDoctor(doctor);

                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorId(int doctorId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE doctor_id = ? ORDER BY appointment_date DESC, appointment_time DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(rs.getInt("appointment_id"));
                appointment.setPatientId(rs.getInt("patient_id"));
                appointment.setDoctorId(rs.getInt("doctor_id"));
                appointment.setAppointmentDate(rs.getDate("appointment_date"));
                appointment.setAppointmentTime(rs.getString("appointment_time"));
                appointment.setVisitType(rs.getString("visit_type"));
                appointment.setStatus(rs.getString("status"));
                appointment.setDiagnosis(rs.getString("diagnosis"));
                appointment.setNote(rs.getString("note"));

                Patient patient = patientDAO.getPatientById(appointment.getPatientId());
                appointment.setPatient(patient);

                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public List<Appointment> getAppointmentsByDate(Date date) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE appointment_date = ? ORDER BY appointment_time";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(date.getTime()));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(rs.getInt("appointment_id"));
                appointment.setPatientId(rs.getInt("patient_id"));
                appointment.setDoctorId(rs.getInt("doctor_id"));
                appointment.setAppointmentDate(rs.getDate("appointment_date"));
                appointment.setAppointmentTime(rs.getString("appointment_time"));
                appointment.setVisitType(rs.getString("visit_type"));
                appointment.setStatus(rs.getString("status"));
                appointment.setDiagnosis(rs.getString("diagnosis"));
                appointment.setNote(rs.getString("note"));
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public List<Appointment> getAppointmentsByStatus(String status) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE status = ? ORDER BY appointment_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(rs.getInt("appointment_id"));
                appointment.setPatientId(rs.getInt("patient_id"));
                appointment.setDoctorId(rs.getInt("doctor_id"));
                appointment.setAppointmentDate(rs.getDate("appointment_date"));
                appointment.setAppointmentTime(rs.getString("appointment_time"));
                appointment.setVisitType(rs.getString("visit_type"));
                appointment.setStatus(rs.getString("status"));
                appointment.setDiagnosis(rs.getString("diagnosis"));
                appointment.setNote(rs.getString("note"));
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments ORDER BY appointment_date DESC, appointment_time DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(rs.getInt("appointment_id"));
                appointment.setPatientId(rs.getInt("patient_id"));
                appointment.setDoctorId(rs.getInt("doctor_id"));
                appointment.setAppointmentDate(rs.getDate("appointment_date"));
                appointment.setAppointmentTime(rs.getString("appointment_time"));
                appointment.setVisitType(rs.getString("visit_type"));
                appointment.setStatus(rs.getString("status"));
                appointment.setDiagnosis(rs.getString("diagnosis"));
                appointment.setNote(rs.getString("note"));
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public ResultSet getPatientAppointmentHistory(int patientId) {
        String sql = "{call GetPatientAppointmentHistory(?)}";
        try {
            Connection conn = DatabaseConnection.getConnection();
            CallableStatement cs = conn.prepareCall(sql);
            cs.setInt(1, patientId);
            return cs.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultSet generateDailyRevenueReport(Date reportDate) {
        String sql = "{call GenerateDailyRevenueReport(?)}";
        try {
            Connection conn = DatabaseConnection.getConnection();
            CallableStatement cs = conn.prepareCall(sql);
            cs.setDate(1, new java.sql.Date(reportDate.getTime()));
            return cs.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultSet getDoctorPerformanceReport(Date startDate, Date endDate) {
        String sql = "{call GetDoctorPerformanceReport(?, ?)}";
        try {
            Connection conn = DatabaseConnection.getConnection();
            CallableStatement cs = conn.prepareCall(sql);
            cs.setDate(1, new java.sql.Date(startDate.getTime()));
            cs.setDate(2, new java.sql.Date(endDate.getTime()));
            return cs.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}