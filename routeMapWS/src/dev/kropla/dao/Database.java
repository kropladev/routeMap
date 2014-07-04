package dev.kropla.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

	public Connection getConnection() throws Exception {
		try {
			String connectionURL = "jdbc:mysql://localhost:3306/rm?characterEncoding=UTF-8";
			Connection connection = null;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(connectionURL, "root",
					"root");
			return connection;
		} catch (SQLException e) {
			System.out.println("DATABASE: SQLexc error");
			throw e;
		} catch (Exception e) {
			System.out.println("DATABASE: other error");
			throw e;
		}
	}

}