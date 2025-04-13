package De2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Connect {
	private static final String URL = "jdbc:mysql://localhost:3306/De2";
	private static final String USER = "root";
	private static final String PASS = "";

	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(URL, USER, PASS);
		} catch (ClassNotFoundException | SQLException ex) {
			Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
}
