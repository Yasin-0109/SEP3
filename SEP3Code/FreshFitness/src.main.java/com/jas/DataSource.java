package com.jas;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSource {

	private static HikariConfig config; // Declare private HikariConfig variable
	private static HikariDataSource ds; // Declare private HikariDataSource variable
	
	static {
		Properties props = new Properties(); // Make properties variable
		props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource"); // Set JDBC driver to PostgreSQL one
		props.setProperty("dataSource.user", Main.getConfig().getProperty("db.user")); // Set login username
		props.setProperty("dataSource.password", Main.getConfig().getProperty("db.password")); // Set login password
		props.setProperty("dataSource.databaseName", Main.getConfig().getProperty("db.databaseName")); // Set database
		props.setProperty("dataSource.portNumber", Main.getConfig().getProperty("db.portNumber")); // Set PostgreSQL server port
		props.setProperty("dataSource.serverName", Main.getConfig().getProperty("db.serverName")); // Set PostgreSQL server location
		props.setProperty("dataSource.currentSchema", Main.getConfig().getProperty("db.currentSchema")); // Set proper schema
		props.put("dataSource.logUnclosedConnections", true);
		//props.setProperty("dataSource.loggerLevel", "DEBUG"); // Set logger level
		props.put("dataSource.logWriter", new PrintWriter(System.out)); // Return logs to System.out
		
		config = new HikariConfig(props); // Initialize new config
		ds = new HikariDataSource(config); // Initialize HikariCP pool
		
		ds.setLeakDetectionThreshold(5000);
		
		ds.setMaximumPoolSize(20);
		ds.setIdleTimeout(30000);
	}
	
	private DataSource() { // Empty initializer
		
	}
	
	/**
	 * Gets HikariCP connection to SQL server.
	 * @return Connection
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
	
}
