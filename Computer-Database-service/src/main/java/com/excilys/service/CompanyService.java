package com.excilys.service;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.om.Company;
import com.excilys.om.Log;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.LogDAO;
import com.excilys.om.Log.TypeLog;

@Service
@Transactional
public class CompanyService 
{
	@Autowired
	private CompanyDAO companyDAO;

	@Autowired
	private LogDAO logDAO;
	
	public List<Company> retrieveAll()
	{
		Log log = Log.builder()
				 .typeLog(TypeLog.retrieve)
				 .operation("Listing des company")
				 .dateLog(LocalDateTime.now())
				 .build();
		
		List<Company> listCompany = companyDAO.findAll();
		
		logDAO.save(log);
	
		return listCompany;
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
