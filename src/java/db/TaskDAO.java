/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import Utils.ConnectionPool;
import Utils.DBUtil;
import business.Tasks;
import com.mysql.jdbc.Statement;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author katie
 */
public class TaskDAO {

    private static final Logger LOG = Logger.getLogger(UserDAO.class.getName());

    private static boolean isDatabaseConnectionValid() throws ClassNotFoundException {
        try {
            ConnectionPool.getInstance().testConnection();
            return true;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Database connection test failed.", e);
            return false;
        }
    }

    public static Tasks addTask(Tasks task) throws SQLException, ClassNotFoundException {

        if (!isDatabaseConnectionValid()) {
            LOG.log(Level.WARNING, "returning default due to an invalid connection.");
            return task;
        }

        try (Connection conn = ConnectionPool.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(" INSERT INTO tasks "
                + " (email, title, description, timestamp, dueDate, isCompleted) "
                + " VALUES (?, ?, ?, ?, ? , ?", Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, task.getEmail());
            ps.setString(2, task.getTitle());
            ps.setString(3, task.getDescription());
            ps.setTimestamp(4, task.getTimestamp());
            ps.setDate(5, task.getDueDate());
            ps.setBoolean(6, task.getIsCompleted());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        task = getTaskById(generatedId);
                    }
                }
                LOG.log(Level.INFO, "task added.");
            } else {
                LOG.log(Level.WARNING, "failed to add task.");
            }
        }
        return task;
    }

    public static void markTaskCompleted(int id) throws SQLException, ClassNotFoundException {

        if (!isDatabaseConnectionValid()) {
            LOG.log(Level.WARNING, "returning due to an invalid connection.");
            return;
        }

        try (Connection conn = ConnectionPool.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement("UPDATE tasks SET isCompleted = true WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.log(Level.WARNING, "Error marking task as complete", e);
        }
    }

    public static void deleteTask(int id) throws SQLException, ClassNotFoundException {

        if (!isDatabaseConnectionValid()) {
            LOG.log(Level.WARNING, "Returning due to an invalid connection.");
            return;
        }

        try (Connection conn = ConnectionPool.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(" DELETE FROM tasks WHERE id = ? ")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.log(Level.WARNING, "error deleting task", e);
        }
    }

    public static void deleteAllTasks() throws SQLException, ClassNotFoundException {
        if (!isDatabaseConnectionValid()) {
            LOG.log(Level.WARNING, "returning due to an invalid connection.");
            return;
        }

        try (Connection conn = ConnectionPool.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement("DELETE FROM tasks")) {
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.log(Level.WARNING, "error deleting all tasks", e);
        }
    }

    public static List<Tasks> selectAll(String email) throws SQLException, ClassNotFoundException {
        List<Tasks> tasks = new ArrayList<>();

        if (!isDatabaseConnectionValid()) {
            LOG.log(Level.WARNING, "returning an empty list due to an invalid connection.");
            return tasks;
        }

        try (Connection conn = ConnectionPool.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement("SELECT * FROM tasks WHERE email = ?")) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LOG.log(Level.INFO, "Retrieved: "
                            + rs.getString("email") + ", "
                            + rs.getString("title") + ", "
                            + rs.getString("description") + ", "
                            + rs.getTimestamp("timestamp") + ", "
                            + rs.getDate("dueDate") + ", "
                            + rs.getBoolean("isCompleted"));

                    Tasks task = new Tasks();
                    task.setEmail(rs.getString("email"));
                    task.setTitle(rs.getString("title"));
                    task.setDescription(rs.getString("description"));
                    task.setTimestamp(rs.getTimestamp("timestamp"));
                    task.setDueDate(rs.getDate("dueDate"));
                    task.setIsCompleted(rs.getBoolean("isCompleted"));

                    tasks.add(task);
                }
            }
        } catch (SQLException e) {
            LOG.log(Level.WARNING, "****error selecting all.", e);
        }
        return tasks;
    }

//    public static List<Tasks> selectAll(String email) throws SQLException, ClassNotFoundException {
//        List<Tasks> tasks = new ArrayList<>();
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        if (!isDatabaseConnectionValid()) {
//            LOG.log(Level.WARNING, "Returning an empty list due to an invalid connection.");
//            return tasks;
//        }
//
//        try {
//            conn = ConnectionPool.getInstance().getConnection();
//            ps = conn.prepareStatement("SELECT * FROM tasks WHERE email = ?");
//            ps.setString(1, email);
//
//            rs = ps.executeQuery();
//
//            while (rs.next()) {
//                LOG.log(Level.INFO, "Retrieved: "
//                        + rs.getString("email") + ", "
//                        + rs.getString("title") + ", "
//                        + rs.getString("description") + ", "
//                        + rs.getTimestamp("timestamp") + ", "
//                        + rs.getDate("dueDate") + ", "
//                        + rs.getBoolean("isCompleted"));
//
//                Tasks task = new Tasks();
//                task.setEmail(rs.getString("email"));
//                task.setTitle(rs.getString("title"));
//                task.setDescription(rs.getString("description"));
//                task.setTimestamp(rs.getTimestamp("timestamp"));
//                task.setDueDate(rs.getDate("dueDate"));
//                task.setIsCompleted(rs.getBoolean("isCompleted"));
//
//                tasks.add(task);
//            }
//        } catch (SQLException e) {
//            LOG.log(Level.WARNING, "Error selecting all.", e);
//        } finally {
//            rs.close();
//            ps.close();
//            conn.close();
//        }
//
//        return tasks;
//    }
    public static Tasks updateTask(Tasks task) throws SQLException, ClassNotFoundException {
        if (!isDatabaseConnectionValid()) {
            LOG.log(Level.WARNING, "returning default due to an invalid connection.");
            return task;
        }

        try (Connection conn = ConnectionPool.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement("UPDATE tasks "
                + " SET title = ?, description = ?, timestamp = ?, dueDate = ?, isCompleted = ? "
                + " WHERE id = ? ")) {

            ps.setString(1, task.getTitle());
            ps.setString(2, task.getDescription());
            ps.setTimestamp(3, task.getTimestamp());
            ps.setDate(4, task.getDueDate());
            ps.setBoolean(5, task.getIsCompleted());
            ps.setInt(6, task.getId());

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                LOG.log(Level.INFO, "Task updated with ID: " + task.getId());
                task = getTaskById(task.getId());
            } else {
                LOG.log(Level.WARNING, "Failure to update task with ID: " + task.getId());
            }
        }
        return task;
    }

    public static Tasks getTaskById(int id) throws SQLException, ClassNotFoundException {
        Tasks task = null;

        if (!isDatabaseConnectionValid()) {
            LOG.log(Level.WARNING, "returning null due to an invalid connection.");
            return task;
        }

        try (Connection conn = ConnectionPool.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement("SELECT * FROM tasks WHERE id = ?")) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    task = new Tasks();

                    task.setId(rs.getInt("id"));
                    task.setEmail(rs.getString("email"));
                    task.setTitle(rs.getString("title"));
                    task.setDescription(rs.getString("description"));
                    task.setTimestamp(rs.getTimestamp("timestamp"));
                    task.setDueDate(rs.getDate("dueDate"));
                    task.setIsCompleted(rs.getBoolean("isCompleted"));
                }
            }
        } catch (SQLException e) {
            LOG.log(Level.WARNING, "error getting task by ID", e);
        }
        return task;
    }

    public static List<Tasks> selectTasksByUserId(int userId) throws SQLException, ClassNotFoundException {
        List<Tasks> taskList = new ArrayList<>();

        if (!isDatabaseConnectionValid()) {
            LOG.log(Level.WARNING, "Returning an empty list due to an invalid connection.");
            return taskList;
        }

        try (Connection conn = ConnectionPool.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement("SELECT * FROM tasks WHERE userId = ?")) {
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Tasks task = new Tasks();

                    task.setId(rs.getInt("id"));
                    task.setEmail(rs.getString("email"));
                    task.setTitle(rs.getString("title"));
                    task.setDescription(rs.getString("description"));
                    task.setTimestamp(rs.getTimestamp("timestamp"));
                    task.setDueDate(rs.getDate("dueDate"));
                    task.setIsCompleted(rs.getBoolean("isCompleted"));

                    taskList.add(task);
                }
            }
        } catch (SQLException e) {
            LOG.log(Level.INFO, "***error getting tasks by user ID", e);
        }

        return taskList;
    }
}
