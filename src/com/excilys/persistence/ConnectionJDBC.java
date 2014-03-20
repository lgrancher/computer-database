package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	private static ThreadLocal<Connection> threadConnect;

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
			threadConnect = new ThreadLocal<Connection>();
			logger.info("Chargement du driver JDBC");
		} 
		
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
		}	
	}
	
	public static ConnectionJDBC getInstance()
	{
		if(connectionJDBC==null)
		{
			connectionJDBC = new ConnectionJDBC();
		}
		
		return connectionJDBC;
	}
	
	public Connection getConnection() throws SQLException
	{	
		if(threadConnect.get() == null)
		{
			Connection connection = connectionPool.getConnection();
			connection.setAutoCommit(false);
			threadConnect.set(connection);
		}
		
		logger.info("Ouverture de la connexion");
		
		return threadConnect.get();
	}
	
	public void close(Connection connection)
	{
		try 
		{	
			connection.close();
			
			logger.info("Fermeture de la connexion");
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		threadConnect.set(null);
	}
	
	public static void close(ResultSet rs, PreparedStatement ps)
	{
		if(rs!=null)
		{
			try 
			{
				rs.close();
			} 
			
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		
		if(ps!=null)
		{
			try 
			{
				ps.close();
			} 
			
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
