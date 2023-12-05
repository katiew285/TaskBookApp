/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import Utils.ConnectionPool;
import business.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Logger;
import java.sql.Date;
import java.sql.SQLException;

/**
 *
 * @author katie
 */
public class RegisterDAO {

    public static int insert(User user) throws SQLException, ClassNotFoundException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement ps = null;

        String query = "INSERT INTO users "
                + "(email, password, name, dob, state)"
                + "VALUES (?, ?, ?, ?, ?)";
        try {

            ps = conn.prepareStatement(query);

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setDate(4, user.getDob());
            ps.setString(5, user.getState());
            ps.executeUpdate();
            
        } catch (SQLException sqlEx) {
            throw sqlEx;
        } finally {
            try {
                ps.close();
                pool.freeConnection(conn);
            } catch (SQLException e) {
                throw e;
            }
        }
        return 0;
    }
}
