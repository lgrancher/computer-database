package com.excilys.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.DTO.CompanyDTO;
import com.excilys.om.Log;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ComputerDAO;
import com.excilys.persistence.ConnectionJDBC;
import com.excilys.persistence.LogDAO;
import com.excilys.om.Log.Type;

@Service
public class CompanyService 
{
	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	
	@Autowired
	private CompanyDAO companyDAO;

	@Autowired
	private LogDAO logDAO;
	
	@Autowired
	private ConnectionJDBC connectionJDBC;
	
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
	
	public CompanyDAO getCompanyDAO() 
	{
		return companyDAO;
	}

	public void setCompanyDAO(CompanyDAO companyDAO) 
	{
		this.companyDAO = companyDAO;
	}

	public LogDAO getLogDAO() 
	{
		return logDAO;
	}

	public void setLogDAO(LogDAO logDAO) 
	{
		this.logDAO = logDAO;
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
