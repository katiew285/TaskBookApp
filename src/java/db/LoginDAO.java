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

     public User select(String email, String password){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        
        try{
            conn = ConnectionPool.getInstance().getConnection();
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            
            if(rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setDob(rs.getDate("dob").toLocalDate());
                user.setState(rs.getString("state"));
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
