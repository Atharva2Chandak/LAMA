package com.wellsfargo.LamaBackend;

import java.sql.SQLException;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.wellsfargo.LamaBackend.databaseSpecifics.DatabaseConnectionWithDriverManager;

@SpringBootApplication
public class LamaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LamaBackendApplication.class, args);
		
		//Boilerplate to get a connection and make a query with JDBC
		String URI = "jdbc:mariadb://localhost:3306/lamadb";
		DatabaseConnectionWithDriverManager connection = new DatabaseConnectionWithDriverManager(URI);
		try {
			System.out.println("database connected: " + connection.connect());
		} catch(SQLException e) {
			ExceptionHandler.printException(e);
		} finally {
			try {
				connection.closeConnection();
			} catch(SQLException e) {
				ExceptionHandler.printException(e);
			}
		}
	} 
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper(); 
	}

}
