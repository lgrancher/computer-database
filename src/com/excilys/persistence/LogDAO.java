package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.excilys.om.Log;

public class LogDAO 
{
	private static LogDAO logDAO;
	
	private LogDAO(){};
	
	public static LogDAO getInstance()
	{
		if(logDAO==null)
		{
			logDAO = new LogDAO();
		}
		
		return logDAO;
	}
	
	public void create(Connection connection, Log log) throws SQLException
	{
		String sql = "insert into log values(default,?,?,NOW())";
		PreparedStatement st=null;
		
		st = connection.prepareStatement(sql);
		st.setString(1, log.getTypeLog());
		st.setString(2, log.getOperation());
		
		st.executeUpdate();	
		ConnectionJDBC.close(null,st);
	}
}
