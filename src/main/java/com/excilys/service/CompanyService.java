package com.excilys.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.joda.time.LocalDate;

import com.excilys.DTO.CompanyDTO;
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
		if (companyService == null) 
		{
			companyService = new CompanyService();
		}

		return companyService;
	}

	public List<CompanyDTO> retrieveAll()
	{
		Log log = Log.builder()
				 .typeLog("retrieve")
				 .operation("Listing des company")
				 .dateLog(new LocalDate())
				 .build();
		
		Connection connection=null;
		List<CompanyDTO> listCompanyDTO=null;
		
		try 
		{
			connection = connectionJDBC.getConnection();
			listCompanyDTO = companyDAO.retrieveAll();
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
		
		return listCompanyDTO;
	}
}
