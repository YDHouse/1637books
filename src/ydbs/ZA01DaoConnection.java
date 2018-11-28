package ydbs;

import java.sql.Connection;
import java.sql.DriverManager;

public class ZA01DaoConnection {
	
	private static Connection conn = null;
	
	private ZA01DaoConnection() {
		
	}
	
    public static Connection getConnection() {
    	 if (conn == null) {
            try {
            	Class.forName("org.mariadb.jdbc.Driver");
            	conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:8008/ydbs", "root", "root1234");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return conn;
    }
    
    public static void close() {
        if (conn != null) {
            try {
                if (!conn.isClosed())
                    conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        conn = null;
    }
    
}