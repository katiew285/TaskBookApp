package Utils;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ConnectionPool {

    private static ConnectionPool pool = null;
    private static DataSource dataSource = null;
    private static final Logger LOG = Logger.getLogger(ConnectionPool.class.getName());
    
    public static void testConnection() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/taskbook";
        String user = "root";
        String password = "";
        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            LOG.log(Level.INFO, "Connection Successful!");
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Connection failed!", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOG.log(Level.SEVERE, "Error closing connection", e);
                }
            }
        }
    }
    
    private ConnectionPool() throws SQLException {
        try {
            InitialContext ic = new InitialContext();
            //This links up with the resource tag in context.xml
            dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/taskbook");
        } catch (NamingException e) {
            LOG.log(Level.SEVERE, "*** failed on datasource lookup", e);
        }
    }

    public static synchronized ConnectionPool getInstance() throws SQLException, ClassNotFoundException {
        if (pool == null) {
            pool = new ConnectionPool();
        }
        return pool;
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void freeConnection(Connection c) {
        try {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** failed on freeing connection", e);
        }
    }
    
    
}
