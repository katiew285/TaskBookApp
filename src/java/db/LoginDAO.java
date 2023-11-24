/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;


import Utils.ConnectionPool;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;


/**
 *
 * @author katie
 */
public class LoginDAO {
    
    private static final Logger LOG = Logger.getLogger(LoginDAO.class.getName());

    public boolean select(String email, String password) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "SELECT * FROM user " +
                " WHERE email = ? AND password = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(2, email);
            ps.setString(3, password);
            rs = ps.executeQuery();
            
            return rs.next();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "***select");
        } finally {
            try {
                if(rs != null){
                    rs.close();
                }
                if(ps != null){
                    ps.close();
                }
                pool.freeConnection(conn);
            } catch (SQLException e){
                LOG.log(Level.SEVERE, "***select null pointer");
            }
        }
        
        if(conn != null){
            pool.freeConnection(conn);
        }
        return false;
    }
}
