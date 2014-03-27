package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.excilys.om.Log;

public enum LogDAO 
{	
	INSTANCE;
	public void create(Log log) throws SQLException
	{
		Connection connection = ConnectionJDBC.INSTANCE.getConnection();
		String sql = "insert into log values(default,?,?,NOW())";
		PreparedStatement st=null;
		
		st = connection.prepareStatement(sql);
		st.setString(1, log.getTypeLog().toString());
		st.setString(2, log.getOperation());
		
		st.executeUpdate();	
		ConnectionJDBC.INSTANCE.close(null,st);
	}
}
