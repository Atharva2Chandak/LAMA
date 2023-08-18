package com.wellsfargo.LamaBackend;

import java.sql.SQLException;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.wellsfargo.LamaBackend.databaseSpecifics.DatabaseConnectionWithDriverManager;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
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
	
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build();                                           
    }

}
