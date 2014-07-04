package dev.kropla.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
//TODO przerobic na datasource???!!
	public Connection getConnection() throws Exception {
		//Logger logger = LoggerFactory.getLogger(this.getClass());
	//	System.out.println("DATABASE: START");
		try {
			String connectionURL = "jdbc:mysql://localhost:3306/rm";
			Connection connection = null;
			Properties info = new Properties();
			info.put("characterEncoding", "UTF-8");
			info.put("user", "root");
			info.put("password", "root");
			info.put("useUnicode", "true");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			//connection = DriverManager.getConnection(connectionURL, "root","root");
			connection = DriverManager.getConnection(connectionURL, info);
			return connection;
		} catch (SQLException e) {
			System.out.println("DATABASE: SQLexc error");
			//logger.error("Error: " + StackTraceWriter.getStackTrace(e.getCause()));
			throw e;
		} catch (Exception e) {
			System.out.println("DATABASE: other error");
			//logger.error("Unknow problem with database. "+e.getMessage());
			throw e;
		}
	}

}