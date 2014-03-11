package com.excilys.jdbc.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionJDBC 
{
	public static Connection connection() throws ClassNotFoundException, SQLException
	{
		String url = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
		String login = "root";
		String password = "excilys";
		Class.forName( "com.mysql.jdbc.Driver" );
		Connection connection = DriverManager.getConnection(url,login,password);

		return connection;
	}

	public static void fermerConnection(Connection connection) throws SQLException
	{
		connection.close();		 
	}
}
