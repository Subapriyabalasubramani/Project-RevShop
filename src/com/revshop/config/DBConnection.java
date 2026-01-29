package com.revshop.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.FileInputStream;
import java.util.Properties;

public class DBConnection {

	static { // created only once when the class is created
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

		} catch (Exception e) {
			System.out.println("Failed to load DB configuration");
			e.printStackTrace();
		}
	}

	private DBConnection() {
	} // prevents from creating object

	public static Connection getConnection() throws SQLException { // called when we want to enable the connection
		
		return DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USERNAME, DatabaseConfig.PASSWORD); //live connection to DB
	}

}
