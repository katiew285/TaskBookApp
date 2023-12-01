/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import Utils.ConnectionPool;
import Utils.DBUtil;
import business.Tasks;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.sql.Date;

/**
 *
 * @author katie
 */
public class TaskDAO {

    public static Tasks addTask(Tasks task) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<String> errors = new ArrayList<>();

        try {
            conn = ConnectionPool.getInstance().getConnection();
            String query = "INSERT INTO tasks (email, title, description, timestamp, dueDate, isCompleted) VALUES (?, ?, ?, ?, ?, ?)";

            ps = conn.prepareStatement(query);

            ps.setString(1, task.getEmail());
            ps.setString(2, task.getTitle());
            ps.setString(3, task.getDescription());
            ps.setTimestamp(4, task.getTimestamp());
            ps.setDate(5, Date.valueOf(task.getDueDate()));
            ps.setBoolean(6, task.getIsCompleted());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {

                if (rs.next()) {

                    task = new Tasks();
                    task.setEmail(rs.getString("email"));
                    task.setTitle(rs.getString("title"));
                    task.setDescription(rs.getString("description"));
                    task.setTimestamp(rs.getTimestamp("timestamp"));
                    task.setDueDate(rs.getDate("dueDate").toLocalDate());
                }
            }

        } catch (SQLException e) {
            errors.add("***error inserting task");
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            ConnectionPool.getInstance().freeConnection(conn);
        }
        return task;
    }

    public static void markTaskCompleted(int id) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<String> errors = new ArrayList<>();

        String query = "UPDATE tasks SET isCompleted = true WHERE id = ?";

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            errors.add("*** error marking task as complete");
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                pool.freeConnection(conn);
            } catch (SQLException e) {
                errors.add("***error - isCompleted");
            }
        }
    }

    public static void deleteTask(int id) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<String> errors = new ArrayList<>();

        String query = "DELETE FROM tasks WHERE id = ?";

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            errors.add("*** error deleting task");
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                pool.freeConnection(conn);
            } catch (Exception e) {
                errors.add("***error - delete");
            }
        }
    }

    public static void deleteAllTasks() {
        ArrayList<String> errors = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = (Connection) pool.getConnection();
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM tasks");
            ps.executeUpdate();
        } catch (SQLException e) {
            errors.add("***error deleting all tasks.");
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(conn);
        }
    }

    public static LinkedHashMap<String, Tasks> selectAll() throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<String> errors = new ArrayList<>();
        LinkedHashMap<String, Tasks> tasks = new LinkedHashMap<>();

        String query = "SELECT * FROM tasks";

        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            Tasks task = null;

            while (rs.next()) {
                task = new Tasks();

                task.setId(rs.getInt("id"));
                task.setEmail(rs.getString("email"));
                task.setDescription(rs.getString("description"));
                task.setTimestamp(rs.getTimestamp("timestamp"));
                task.setDueDate(rs.getDate("dueDate").toLocalDate());
                task.setIsCompleted(rs.getBoolean("isCompleted"));

                tasks.put(Integer.toString(task.getId()), task);
            }
            return tasks;
        } catch (SQLException e) {
            errors.add("***error selecting all tasks");
        } finally {
            try {
                //rs.close();
                //ps.close();
                DBUtil.closeResultSet(rs);
                DBUtil.closePreparedStatement(ps);
                DBUtil.closeStatement(ps);
                pool.freeConnection(conn);
            } catch (Exception e) {
                errors.add("***error - select all");
            }
        }
        return null;
    }
}
