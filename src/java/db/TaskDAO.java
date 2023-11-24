/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import Utils.ConnectionPool;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

/**
 *
 * @author katie
 */
public class TaskDAO {

    public static void addTask(String id) {

        String message = "";

        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection conn = (Connection) pool.getConnection();
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement("INSERT INTO tasks"
                    + "(title, description, timestamp, dueDate) "
                    + "WHERE id = ?");

            ps.setInt(1, Integer.parseInt(id));
            ps.executeUpdate();
        } catch (SQLException e) {
            message = "Error adding task.";
        }
    }

    public static void deleteTask(String id) {
        String message = "";

        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection conn = (Connection) pool.getConnection();
            PreparedStatement ps = (PreparedStatement) conn.prepareCall("DELETE FROM tasks"
                    + " WHERE id = ?");

            ps.setInt(1, Integer.parseInt(id));
            ps.executeUpdate();
        } catch (SQLException e) {
            message = "Error deleting task.";
        }
    }

    public static void deleteAllTasks() {
        String message = "";

        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection conn = (Connection) pool.getConnection();
            PreparedStatement ps = (PreparedStatement) conn.prepareCall(" DELETE FROM tasks");

            ps.executeUpdate();
        } catch (SQLException e) {
            message = "Error deleting all tasks.";
        }
    }

    public static List<String> getAllTasks() {
        List<String> tasks = new ArrayList<>();
        String message = "";

        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection conn = (Connection) pool.getConnection();
            PreparedStatement ps = (PreparedStatement) conn.prepareCall("SELECT * FROM tasks");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                tasks.add(rs.getString("title"));
            }
        } catch (SQLException e) {
            message = "Error getting all tasks.";
        }
        return tasks;
    }

}
