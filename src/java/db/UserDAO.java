/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import Utils.ConnectionPool;
import Utils.DBUtil;
import business.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 *
 * @author katie
 */
public class UserDAO {

    String message = "";

    public LinkedHashMap<String, User> getUserByEmail(String email) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = (Connection) pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM users WHERE email = ?";

        try {
            ps = (PreparedStatement) conn.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();

            LinkedHashMap<String, User> users = new LinkedHashMap();

            while (rs.next()) {
                User u = new User();

                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setRoles(getRolesForUser(email));

                users.put(u.getEmail(), u);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving user by email", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                pool.freeConnection(conn);
            } catch (Exception e) {
                throw new RuntimeException("Error closing resources", e);
            }
        }
    }

    private Set<String> getRolesForUser(String email) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = (Connection) pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Set<String> roles = new HashSet<>();

        String query = "SELECT role FROM user_roles WHERE username = ?";

        try {
            ps = (PreparedStatement) conn.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();

            while (rs.next()) {
                roles.add(rs.getString("role"));
            }
        } catch (SQLException e) {
            message += e;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                pool.freeConnection(conn);
            } catch (SQLException e) {
                message += e;
            }
        }
        return roles;
    }

    public static boolean doesEmailExist(String email) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT Email FROM users "
                + "WHERE email = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(conn);
        }
    }

    public static LinkedHashMap<String, User> selectAll() throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM users";

        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            User user = null;
            LinkedHashMap<String, User> users = new LinkedHashMap();

            while (rs.next()) {
                user = new User();

                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setDob(rs.getDate("dob").toLocalDate());
                user.setState(rs.getString("state"));

                users.put(user.getEmail(), user);
            }
            return users;
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                if (rs != null || ps != null) {
                    rs.close();
                    ps.close();
                }
                pool.freeConnection(conn);
            } catch (Exception e) {
                throw e;
            }
        }
    }
}
