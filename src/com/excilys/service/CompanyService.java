package com.excilys.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.excilys.om.Company;
import com.excilys.om.Log;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ConnectionJDBC;
import com.excilys.persistence.LogDAO;

public class CompanyService 
{
	private static CompanyService companyService;
	private static CompanyDAO companyDAO;
	private static LogDAO logDAO;
	
	private CompanyService()
	{
		companyDAO = CompanyDAO.getInstance();
		logDAO = LogDAO.getInstance();
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
		
		Log log = Log.builder()
				 .typeLog("retrieve")
				 .operation("Listing des company")
				 .dateLog(new Date())
				 .build();
	
		logDAO.create(connection, log);
		
		List<Company> listCompany = companyDAO.retrieveAll(connection);
		ConnectionJDBC.close(connection);
		
		return listCompany;
	}
}
