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

public class CompanyService {
	private static CompanyService companyService;
	private static CompanyDAO companyDAO;
	private static LogDAO logDAO;
	private static ConnectionJDBC connectionJDBC;

	private CompanyService() 
	{
		companyDAO = CompanyDAO.getInstance();
		logDAO = LogDAO.getInstance();
		connectionJDBC = ConnectionJDBC.getInstance();
	}

	public static CompanyService getInstance() 
	{
		if (companyService == null) {
			companyService = new CompanyService();
		}

		return companyService;
	}

	public List<Company> retrieveAll()
	{
		Log log = Log.builder()
				 .typeLog("retrieve")
				 .operation("Listing des company")
				 .dateLog(new Date())
				 .build();
		
		Connection connection=null;
		List<Company> listCompany=null;
		
		try 
		{
			connection = connectionJDBC.getConnection();
			listCompany = companyDAO.retrieveAll();
			logDAO.create(log);
			connection.commit();
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		finally
		{
			if(connection!=null)
			{
				connectionJDBC.close(connection);
			}
		}
		
		return listCompany;
	}
}
