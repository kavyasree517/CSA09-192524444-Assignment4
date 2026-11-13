import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationDAO {
    public void insertRegistration(Registration registration) throws SQLException {
        String sql = "INSERT INTO registrations (registration_number, student_name, event_id) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, registration.getRegistrationNumber());
            statement.setString(2, registration.getStudentName());
            statement.setInt(3, registration.getEventId());
            int rows = statement.executeUpdate();
            if (rows == 0) {
                throw new SQLException("No row inserted.");
            }
        }
    }

    public List<Event> loadEvents() throws SQLException {
        String sql = "SELECT event_id, event_name, event_date, venue FROM events ORDER BY event_name";
        List<Event> events = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                events.add(new Event(rs.getInt("event_id"), rs.getString("event_name"), rs.getString("event_date"), rs.getString("venue")));
            }
        }
        return events;
    }

    public List<Registration> loadRegistrations(int offset, int limit) throws SQLException {
        String sql = "SELECT r.registration_id, r.registration_number, r.student_name, r.event_id, e.event_name, r.registration_date " +
                "FROM registrations r JOIN events e ON r.event_id = e.event_id ORDER BY r.registration_id LIMIT ? OFFSET ?";
        List<Registration> list = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    list.add(new Registration(rs.getInt("registration_id"), rs.getString("registration_number"), rs.getString("student_name"), rs.getInt("event_id"), rs.getString("event_name"), rs.getString("registration_date")));
                }
            }
        }
        return list;
    }

    public List<Registration> filterRegistrations(String eventName, int offset, int limit) throws SQLException {
        String sql = "SELECT r.registration_id, r.registration_number, r.student_name, r.event_id, e.event_name, r.registration_date " +
                "FROM registrations r JOIN events e ON r.event_id = e.event_id WHERE e.event_name = ? ORDER BY r.registration_id LIMIT ? OFFSET ?";
        List<Registration> list = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, eventName);
            statement.setInt(2, limit);
            statement.setInt(3, offset);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    list.add(new Registration(rs.getInt("registration_id"), rs.getString("registration_number"), rs.getString("student_name"), rs.getInt("event_id"), rs.getString("event_name"), rs.getString("registration_date")));
                }
            }
        }
        return list;
    }

    public boolean isDuplicateRegistration(String registrationNumber, int eventId) throws SQLException {
        String sql = "SELECT 1 FROM registrations WHERE registration_number = ? AND event_id = ?";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, registrationNumber);
            statement.setInt(2, eventId);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void exportCsv(File file, List<Registration> registrations) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("registration_id,registration_number,student_name,event_name,registration_date");
            writer.newLine();
            for (Registration r : registrations) {
                writer.write(r.getRegistrationId() + "," + r.getRegistrationNumber() + "," + r.getStudentName() + "," + r.getEventName() + "," + r.getRegistrationDate());
                writer.newLine();
            }
        }
    }
}
