package org.in.com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoConnection {
	public static Connection connection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ezee_bank?connectionnectTimeout=5000&socketTimeout=30000&appautoReconnectionnect=true&useSSL=false", "root", "root");

		return connection;
	}

	
}
