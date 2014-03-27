package com.excilys.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.DTO.CompanyDTO;
import com.excilys.om.Log;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ComputerDAO;
import com.excilys.persistence.ConnectionJDBC;
import com.excilys.persistence.LogDAO;
import com.excilys.om.Log.Type;

public class CompanyService {
	private static CompanyService companyService;
	private static CompanyDAO companyDAO;
	private static LogDAO logDAO;
	private static ConnectionJDBC connectionJDBC;
	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

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
				 .typeLog(Type.retrieve)
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
			logger.info("Listing des company");
		} 
		
		catch (SQLException e) 
		{
			logger.info("Erreur de listing des company");
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
