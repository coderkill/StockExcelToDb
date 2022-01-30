package common;

import com.mysql.jdbc.Connection;
import org.apache.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    final static Logger logger = Logger.getLogger(Connector.class.getName());
    static String url = "jdbc:mysql://localhost:3306/olam?useSSL=false";
    static String user = "root";
    static String password = "root";
    private static Connection con = null;

    public static void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if (con == null || con.isClosed()) {
                con = (Connection) DriverManager.getConnection(url, user, password);
            }
            return con;
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e);
        }
        return null;
    }

}
