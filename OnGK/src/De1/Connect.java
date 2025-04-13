package De1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connect {
	private static Connection conn = null;
	public static Connection getConnection() {
		
		if(conn== null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost/De1", "root", "");
				
			} catch (ClassNotFoundException | SQLException ex) {
				Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return conn;
	}
	
	public static void close() {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException ex) {
				Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
