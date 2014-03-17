package com.excilys.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import org.slf4j.*;

public class ConnectionJDBC 
{
	private static ConnectionJDBC connectionJDBC;
	private static String url = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private static String user = "root";
	private static String passwd = "excilys";
	private static Logger logger = LoggerFactory.getLogger(ConnectionJDBC.class);
	private static BoneCP connectionPool = null;

	private ConnectionJDBC()
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl(url);
			config.setUsername(user);
			config.setPassword(passwd);
			config.setMinConnectionsPerPartition(5);
			config.setMaxConnectionsPerPartition(10);
			config.setPartitionCount(1);
			connectionPool = new BoneCP(config);	
			logger.info("Chargement du driver JDBC");
		} 
		
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
		}	
	}
	
	public static Connection getConnection() throws SQLException
	{	
		if(connectionJDBC==null)
		{
			connectionJDBC = new ConnectionJDBC();
		}
		
		Connection connection = connectionPool.getConnection();

		logger.info("Ouverture de la connection");
		return connection;
	}
	
	public static void close(Connection connection)
	{
		try 
		{
			logger.info("Fermeture de la connexion");
			connection.close();
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}
