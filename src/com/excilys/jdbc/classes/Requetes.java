package com.excilys.jdbc.classes;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class Requetes 
{
	private Statement st;
	
	public Requetes(Statement st)
	{
		this.st = st;
	}
	
	public ArrayList<ComputerDAO> getListComputers() throws SQLException
	{
		ResultSet rs = st.executeQuery("select * from computer");
		
		ArrayList<ComputerDAO> listeComputers = new ArrayList<ComputerDAO>();
		
		while(rs.next())
		{
			int id = rs.getInt(1);
			String name = rs.getString(2);
			Date introduced = rs.getDate(3);
			Date discontinued = rs.getDate(4);
			int company_id = rs.getInt(5);
			
			ComputerDAO computer = new ComputerDAO(id, name, introduced, discontinued, company_id);
			
			listeComputers.add(computer); 
		}

		return listeComputers;
	}
	
	public ArrayList<CompanyDAO> getListCompany() throws SQLException
	{
		ResultSet rs = st.executeQuery("select * from company");
		
		ArrayList<CompanyDAO> listeCompany = new ArrayList<CompanyDAO>();
		
		while(rs.next())
		{	
			CompanyDAO company = new CompanyDAO(rs.getInt(1),rs.getString(2));
			listeCompany.add(company);
		}

		return listeCompany;
	}
}
