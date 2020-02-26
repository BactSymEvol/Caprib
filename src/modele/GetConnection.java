package modele;

import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class GetConnection {
	private Connection con;
	private String dbName;

	public GetConnection(String dbName) {
		this.dbName = dbName;
	}

	public GetConnection() {
		this.dbName = null;
	}

	public Connection getConnection() throws SQLException {
		try {
			String e = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/";
			String username = "root";
			String password = "";
			Class.forName(e);
			this.con = DriverManager.getConnection(url, username, password);
			System.out.println("Connected to databases");
			return this.con;
		}catch (Exception arg4) {
			
			JOptionPane.showMessageDialog((Component) null, "There is not connection with database, please start XAMPP and restart CAPRIB" , "Warning", 2);
			
			
			System.exit(0);
			return null;
		}
	}

	public Connection getConnection(String dbname) {
		this.dbName = dbname;

		try {
			String e = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/caprib_" + this.dbName;
			String username = "root";
			String password = "";
			Class.forName(e);
			this.con = DriverManager.getConnection(url, username, password);
			System.out.println("Connected to "+ dbname);
			return this.con;
		} catch (Exception arg5) {
			System.out.println(arg5);
			return null;
		}
	}
}
