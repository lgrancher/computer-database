package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.om.Company;

public class CompanyDAO 
{
	private static CompanyDAO companyDAO;
	
	private CompanyDAO()
	{
		
	}
	
	public static CompanyDAO getInstance()
	{
		if(companyDAO==null)
		{
			companyDAO = new CompanyDAO();
		}
		
		return companyDAO;
	}
	
	public List<Company> retrieveAll() throws SQLException
	{
		Connection connection = ConnectionJDBC.getInstance();
		String sql = "select * from company";
		
		PreparedStatement st = connection.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		
		ArrayList<Company> listeCompany = new ArrayList<Company>();
		
		while(rs.next())
		{
			Company company = new Company();
			
			company.setId(rs.getLong(1));
			company.setName(rs.getString(2));
			
			listeCompany.add(company);
		}
		
		ConnectionJDBC.close(connection);
		
		return listeCompany;
	}
	
	public Company find(Long id) throws SQLException
	{
		Connection connection = ConnectionJDBC.getInstance();
		String sql = "select * from computer where id=?";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setLong(1, id);
		
		ResultSet rs = st.executeQuery();
		rs.next();
		
		Company company = new Company();
		
		company.setId(rs.getLong(1));
		company.setName(rs.getString(2));
		
		ConnectionJDBC.close(connection);
		
		return company;
	}
	
}
