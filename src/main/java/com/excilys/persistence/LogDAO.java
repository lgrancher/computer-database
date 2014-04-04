package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.om.Log;

@Repository
public class LogDAO 
{	
	@Autowired
	private ConnectionJDBC connectionJDBC;
	
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

	public ConnectionJDBC getConnectionJDBC() 
	{
		return connectionJDBC;
	}

	public void setConnectionJDBC(ConnectionJDBC connectionJDBC) 
	{
		this.connectionJDBC = connectionJDBC;
	}
}
