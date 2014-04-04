package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ConnectionJDBC 
{
	private static Logger logger = LoggerFactory.getLogger(ConnectionJDBC.class);
	private static ThreadLocal<Connection> threadConnect;

	@Autowired
	private DataSource dataSource;
	
	private ConnectionJDBC()
	{
		threadConnect = new ThreadLocal<Connection>();
		logger.info("Initialisation de la connexion");
	}
	
	public Connection getConnection() throws SQLException
	{	
		if(threadConnect.get() == null)
		{
			Connection connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			threadConnect.set(connection);
			logger.info("Ouverture de la connexion");
		}
				
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
			logger.error("Erreur lors de la fermeture de la connexion");
		}
		
		finally
		{
			threadConnect.remove();
		}
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
				logger.error("Erreur lors de la fermeture du ResultSet");
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
				logger.error("Erreur lors de la fermeture du PreparedStatement");
			}
		}
	}
}