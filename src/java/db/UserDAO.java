/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import Utils.ConnectionPool;
import Utils.DBUtil;
import business.User;
import java.util.logging.Level;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author katie
 */
public class UserDAO {

    private static final Logger LOG = Logger.getLogger(UserDAO.class.getName());

    public static User getUserByEmail(String email) throws SQLException, ClassNotFoundException {
        User user = null;

        if (!isDatabaseConnectionValid()) {
            LOG.log(Level.WARNING, "returning default due to invalid database connection.");
            return user;
        }

        try (Connection conn = ConnectionPool.getConnection(); PreparedStatement ps = conn.prepareStatement("SELECT email,password,name,dob,state FROM users WHERE email=?")) {
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
//                    LOG.log(Level.INFO, "Retrieved: "
//                            + rs.getString("email") + ", "
//                            + rs.getString("password") + ", "
//                            + rs.getString("name") + ", "
//                            + rs.getDate("dob") + ", "
//                            + rs.getString("state"));

                    user = new User();
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setName(rs.getString("name"));
                    user.setDob(rs.getDate("dob"));
                    user.setState(rs.getString("state"));

                }
            }
        }
        return user;
    }

    private static boolean isDatabaseConnectionValid() throws ClassNotFoundException {
        try {
            ConnectionPool.getInstance().testConnection();
            return true;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Database connection test failed.", e);
            return false;
        }
    }

    //try1
    //    public static User getUserByEmail(String email) throws SQLException, ClassNotFoundException {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        User user = null;
//        
//        String query = "SELECT email, name, dob, state FROM users WHERE email=? ";
//        
//        if (!isDatabaseConnectionValid()) {
//            LOG.log(Level.WARNING, "returning default due to invalid database connection.");
//            return user;
//        }
//
//        try {
//            conn = ConnectionPool.getInstance().getConnection();
//            LOG.log(Level.INFO, "Connected.");
//
//            ps = conn.prepareStatement(query);
//            ps.setString(1, email);
//            rs = ps.executeQuery();
//
//            if (rs.next()) {
//                user = new User();
//                user.setEmail(rs.getString("email"));
//                user.setName(rs.getString("name"));
//                user.setDob(rs.getDate("dob"));
//                user.setState(rs.getString("state"));
//                
//                LOG.log(Level.INFO, "Email: " + user.getEmail());
//                LOG.log(Level.INFO, "Name: " + user.getName());
//                LOG.log(Level.INFO, "DOB: " + user.getDob());
//                LOG.log(Level.INFO, "State: " + user.getState());
//            }
//        } catch (SQLException e) {
//            LOG.log(Level.SEVERE, "***error with getting user by email", e);
//        } finally {
//            DBUtil.closeResultSet(rs);
//            DBUtil.closePreparedStatement(ps);
//            ConnectionPool.getInstance().freeConnection(conn);
//            LOG.log(Level.INFO, "Connection closed.");
//        }
//        return user;
//    }
    //Original
//    public static User getUserByEmail(String email) throws SQLException, ClassNotFoundException {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        User user = null;
//
//        try {
//            conn = ConnectionPool.getInstance().getConnection();
//            String query = "SELECT email,name,dob,state FROM users WHERE email = ?";
//
//            ps = conn.prepareStatement(query);
//            ps.setString(1, email);
//            rs = ps.executeQuery();
//
//            if (rs.next()) {
//                user = new User();
//                user.setEmail(rs.getString("email"));
//                user.setName(rs.getString("name"));
//                user.setDob(rs.getDate("dob"));
//                user.setState(rs.getString("state"));
//            }
//        } catch (SQLException e) {
//            LOG.log(java.util.logging.Level.SEVERE, "***error with select", e);
//        } finally {
//            DBUtil.closeResultSet(rs);
//            DBUtil.closePreparedStatement(ps);
//            ConnectionPool.getInstance().freeConnection(conn);
//        }
//        return user;
//    }
//    public static List<User> selectAll() throws SQLException, ClassNotFoundException {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection conn = pool.getConnection();
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        List<User> userList = new ArrayList<>();
//
//        String query = "SELECT * FROM users";
//
//        try {
//            ps = conn.prepareStatement(query);
//            rs = ps.executeQuery();
//
//            while (rs.next()) {
//                User user = new User();
//
//                userList.add(user);
//            }
//        } catch (SQLException e) {
//            throw e;
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (ps != null) {
//                    ps.close();
//                }
//                pool.freeConnection(conn);
//            } catch (SQLException e) {
//                throw e;
//            } catch (Exception e) {
//                throw e;
//            }
//        }
//        return userList;
//    }
    
    public static List<User> selectAll() throws SQLException, ClassNotFoundException {
    List<User> userList = new ArrayList<>();

    if (!isDatabaseConnectionValid()) {
        LOG.log(Level.WARNING, "Returning null due to an invalid connection.");
        return userList;
    }

    ConnectionPool pool = ConnectionPool.getInstance();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        conn = pool.getConnection();
        String query = "SELECT * FROM users";
        ps = conn.prepareStatement(query);
        rs = ps.executeQuery();

        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setName(rs.getString("name"));
            user.setDob(rs.getDate("dob"));
            user.setState(rs.getString("state"));

            userList.add(user);
        }
    } catch (SQLException e) {
        LOG.log(Level.WARNING, "Error selecting all users", e);
    }
    return userList;
}

    public static User update(User user) throws SQLException, ClassNotFoundException {
       User updatedUser = null; 
       
       if(!isDatabaseConnectionValid()){
           LOG.log(Level.WARNING, "Returning default due to an invalid connection.");
           return updatedUser;
       }
       
       try(Connection conn = ConnectionPool.getConnection();
           PreparedStatement ps = conn.prepareStatement(" UPDATE users " +
                   " SET password = ?, name = ?, dob = ?, state = ? " +
                   " WHERE email = ? ")){
           ps.setString(1, user.getPassword());
           ps.setString(2, user.getName());
           ps.setDate(3, user.getDob());
           ps.setString(4, user.getState());
           ps.setString(5, user.getEmail());
           
           int rowsUpdated = ps.executeUpdate();
           
           if(rowsUpdated > 0){
               LOG.log(Level.INFO, "profile updated for user: " + user.getEmail());
               user = getUserByEmail(user.getEmail());
           } else { 
               LOG.log(Level.WARNING, "failure to update for user: " + user.getEmail());
           }
       }
       return updatedUser;
    }
    
    public static boolean deleteUserById(int userId) throws SQLException, ClassNotFoundException {
    
        if (!isDatabaseConnectionValid()) {
        LOG.log(Level.WARNING, "Returning false due to an invalid connection.");
        return false;
    }

    try (Connection conn = ConnectionPool.getInstance().getConnection();
         PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE id = ?")) {
        ps.setInt(1, userId);

        int rowsDeleted = ps.executeUpdate();

        return rowsDeleted > 0;
    } catch (SQLException e) {
        LOG.log(Level.INFO, "error deleting user by ID", e);
        return false;
    }
}

}