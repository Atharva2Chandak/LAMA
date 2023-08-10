package com.wellsfargo.LamaBackend.databaseSpecifics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionWithDriverManager {
	private Connection connection;
	private String connectionURI;
	private String username, password;
	public DatabaseConnectionWithDriverManager(String URI) {
		connection = null;
		connectionURI = URI;
		username = "root";
		password = "Password@1";
	}
	public boolean connect() throws SQLException {
		this.connection = DriverManager.getConnection(this.connectionURI, username, password);
		return checkConnectionStatus();
	}
	public void closeConnection() throws SQLException {
		if(connection != null) {
			connection.close();
		}
	}
	public boolean checkConnectionStatus() throws SQLException {
		if(connection != null) {			
			return this.connection.isValid(2);
		}
		return false;
	}
}
