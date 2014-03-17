package com.excilys.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.excilys.om.Company;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ConnectionJDBC;

public class CompanyService 
{
	private static CompanyService companyService;
	private static CompanyDAO companyDAO;
	
	private CompanyService()
	{
		companyDAO = CompanyDAO.getInstance();
	}
	
	public static CompanyService getInstance()
	{
		if(companyService==null)
		{
			companyService = new CompanyService();
		}
		
		return companyService;
	}
	
	public List<Company> retrieveAll() throws SQLException
	{
		Connection connection = ConnectionJDBC.getConnection();
		List<Company> listCompany = companyDAO.retrieveAll(connection);
		ConnectionJDBC.close(connection);
		
		return listCompany;
	}
	
	public Company find(Long id) throws SQLException
	{
		Connection connection = ConnectionJDBC.getConnection();
		Company company = companyDAO.find(connection, id);
		ConnectionJDBC.close(connection);
		
		return company;
	}
}
