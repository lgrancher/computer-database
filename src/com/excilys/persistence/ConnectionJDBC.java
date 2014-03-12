package com.excilys.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionJDBC 
{
	private static ConnectionJDBC connectionJDBC;
	private static String url = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private static String user = "root";
	private static String passwd = "excilys";

	private ConnectionJDBC()
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		} 
		
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}

	public static Connection getInstance()
	{
		
		if(connectionJDBC==null)
		{
			connectionJDBC = new ConnectionJDBC();
		}
		
		Connection connect=null;
	
		try
		{
			connect = DriverManager.getConnection(url, user, passwd);
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	
		return connect;	
	}
	
	public static void close(Connection connection)
	{
		try 
		{
			connection.close();
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}
