package com.excilys.jdbc.classes;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Computer 
{
	private Statement st;
	
	public Computer(Statement st)
	{
		this.st = st;
	}
	
	public ArrayList<String[]> getListComputers() throws SQLException
	{
		ResultSet rs = st.executeQuery("select * from computer");
		ResultSetMetaData resultMeta = rs.getMetaData();
		
		ArrayList<String[]> listeComputers = new ArrayList<String[]>();
		
		String[] titres = new String[resultMeta.getColumnCount()];
		
		for(int i = 1; i <= resultMeta.getColumnCount(); i++)
	    {
			titres[i-1] = resultMeta.getColumnName(i).toUpperCase();   
	    }
		
		listeComputers.add(titres);
		
		while(rs.next())
		{
			String[] donnees = new String[resultMeta.getColumnCount()];
			
			for(int i=0; i<donnees.length; i++)
			{
				donnees[i] = rs.getString(i+1);
			}
			
			listeComputers.add(donnees); 
		}

		return listeComputers;
	}
}
