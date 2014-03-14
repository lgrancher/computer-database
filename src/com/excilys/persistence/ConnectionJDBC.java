package com.excilys.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.*;

public class ConnectionJDBC 
{
	private static ConnectionJDBC connectionJDBC;
	private static String url = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private static String user = "root";
	private static String passwd = "excilys";
	private static Logger logger = LoggerFactory.getLogger(ConnectionJDBC.class);

	private ConnectionJDBC()
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			logger.info("Chargement du driver JDBC");
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
			logger.info("Ouverture de la connection");
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
			logger.info("Fermeture de la connection");
			connection.close();
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}
