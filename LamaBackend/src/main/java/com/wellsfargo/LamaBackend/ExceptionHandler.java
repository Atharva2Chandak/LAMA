package com.wellsfargo.LamaBackend;

import java.sql.SQLException;

public class ExceptionHandler {
	public static void printException(SQLException e) {
		System.out.println(e.getMessage());
		System.out.println(e.getSQLState());
		
		System.out.println(e.getErrorCode());
		e.printStackTrace();
	}

}
