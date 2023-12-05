/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;


import Utils.ConnectionPool;
import Utils.DBUtil;
import business.User;
import java.util.logging.Level;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.logging.Logger;
import java.sql.Connection;


/**
 *
 * @author katie
 */
public class LoginDAO {
  
    
private static final Logger LOG = Logger.getLogger(LoginDAO.class.getName());

     public static User select(String email, String password) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        
         if (!isDatabaseConnectionValid()) {
            LOG.log(Level.WARNING, "returning default due to invalid database connection.");
            return user;
        }
        
        try{
            conn = ConnectionPool.getInstance().getConnection();
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            
            if(rs.next()){
                user = new User();
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
              }
        } catch (SQLException e){
            LOG.log(Level.SEVERE, "***error with select", e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            ConnectionPool.getInstance().freeConnection(conn);
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
   
//    public static User select(String email, String password) {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        String query = "SELECT * FROM users "
//                + "WHERE email = ? AND password = ?";
//        try {
//            ps = connection.prepareStatement(query);
//            ps.setString(1, email);
//            ps.setString(2, password);
//            rs = ps.executeQuery();
//            User user = null;
//            
//            if (rs.next()) {
//                user = new User();
//                user.setEmail(rs.getString("email"));
//                user.setPassword(rs.getString("password"));
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//        } finally {
//            DBUtil.closeResultSet(rs);
//            DBUtil.closePreparedStatement(ps);
//            pool.freeConnection(connection);
//        }
//        return null;
//    }
}
