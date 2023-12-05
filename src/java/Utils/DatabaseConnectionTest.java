/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.sql.SQLException;

/**
 *
 * @author katie
 */
public class DatabaseConnectionTest {
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException{
        ConnectionPool connPool = ConnectionPool.getInstance();
        
        connPool.testConnection();
    }
}
