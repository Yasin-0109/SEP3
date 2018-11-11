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
		props.setProperty("dataSource.user", "postgres"); // Set login username
		props.setProperty("dataSource.password", "ecq65tah"); // Set login password
		props.setProperty("dataSource.databaseName", "postgres"); // Set database
		props.setProperty("dataSource.portNumber", "5432"); // Set PostgreSQL server port
		props.setProperty("dataSource.serverName", "localhost"); // Set PostgreSQL server location
		props.put("dataSource.logWriter", new PrintWriter(System.out)); // Return logs to System.out
		
		config = new HikariConfig(props); // Initialize new config
		ds = new HikariDataSource(config); // Initialize HikariCP pool
	}
	
	private DataSource() {
		
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
