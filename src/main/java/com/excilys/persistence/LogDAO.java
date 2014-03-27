package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.excilys.om.Log;

public class LogDAO 
{
	private static LogDAO logDAO;
	private static ConnectionJDBC connectionJDBC;
	
	private LogDAO()
	{
		connectionJDBC = ConnectionJDBC.getInstance();
	}
	
	public static LogDAO getInstance()
	{
		if(logDAO==null)
		{
			logDAO = new LogDAO();
		}
		
		return logDAO;
	}
	
	public void create(Log log) throws SQLException
	{
		Connection connection = connectionJDBC.getConnection();
		String sql = "insert into log values(default,?,?,NOW())";
		PreparedStatement st=null;
		
		st = connection.prepareStatement(sql);
		st.setString(1, log.getTypeLog().toString());
		st.setString(2, log.getOperation());
		
		st.executeUpdate();	
		ConnectionJDBC.close(null,st);
	}
}
