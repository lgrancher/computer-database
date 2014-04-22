package com.excilys.service;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.DTO.CompanyDTO;
import com.excilys.om.Log;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ComputerDAO;
import com.excilys.persistence.LogDAO;
import com.excilys.om.Log.TypeLog;

@Service
@Transactional
public class CompanyService 
{
	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	
	@Autowired
	private CompanyDAO companyDAO;

	@Autowired
	private LogDAO logDAO;
	
	public List<CompanyDTO> retrieveAll()
	{
		Log log = Log.builder()
				 .typeLog(TypeLog.retrieve)
				 .operation("Listing des company")
				 .dateLog(LocalDateTime.now())
				 .build();
		
		List<CompanyDTO> listCompanyDTO = companyDAO.retrieveAll();
		
		logDAO.create(log);
		logger.info("Listing des company");
	
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
}
