package com.revshop.config;

import java.sql.Connection;
import java.sql.DriverManager;
import org.apache.log4j.Logger;
import java.sql.SQLException;

public class DBConnection {
	private static final Logger logger =
            Logger.getLogger(DBConnection.class);

	static { // created only once when the class is created
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			logger.info("Oracle JDBC Driver loaded successfully");

		} catch (Exception e) {
			System.out.println("Failed to load DB configuration");
			logger.error("Failed to load Oracle JDBC Driver", e);
			e.printStackTrace();
		}
	}

	private DBConnection() {
	} // prevents from creating object

	public static Connection getConnection() throws SQLException { // called when we want to enable the connection
		logger.info("Creating database connection");
		
		return DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USERNAME, DatabaseConfig.PASSWORD); //live connection to DB
	}

}
