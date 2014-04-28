package com.excilys.service;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.excilys.om.Computer;
import com.excilys.om.Log;
import com.excilys.om.Log.TypeLog;
import com.excilys.om.MessageLog;
import com.excilys.om.PageGenerator;
import com.excilys.persistence.ComputerDAO;
import com.excilys.persistence.LogDAO;

@Service
@Transactional
public class ComputerService 
{	
	@Autowired
	private ComputerDAO computerDAO;
	
	@Autowired
	private LogDAO logDAO;
		
	public Page<Computer> retrieve(PageGenerator pageGen)
	{
		Pageable pageRequest = pageGen.retrievePageable();
		
		Page<Computer> page = computerDAO.findAll(pageGen.retrieveSearch(), pageRequest);
		
		String operation = MessageLog.getRetrieve(page, pageGen.getSearch());
		
		Log log = Log.builder()
				 .typeLog(TypeLog.retrieve)
				 .operation(operation)
				 .dateLog(LocalDateTime.now())
				 .build();
		
	    logDAO.save(log);
	    
		return page;
	}
	
	public List<Computer> findAll()
	{
		return computerDAO.findAll();
	}
	
	public void create(Computer computer) 
	{			
		computerDAO.save(computer);
		long id = computer.getId();
		
		String operation = MessageLog.getCreate(computer.getName(), id);
		
		Log log = Log.builder()
				 .typeLog(TypeLog.create)
				 .operation(operation)
				 .dateLog(LocalDateTime.now())
				 .build();
		
		logDAO.save(log);
	}
	
	public Computer find(Long id)
	{		
		Computer computer = computerDAO.findOne(id);

		String operation = MessageLog.getFind(id);
		Log log = Log.builder()
				 .typeLog(TypeLog.find)
				 .operation(operation)
				 .dateLog(LocalDateTime.now())
				 .build();
		
		logDAO.save(log);

		return computer;
	}
	
	public void update(Computer computer) 
	{		
		computerDAO.save(computer);
			
		String operation = MessageLog.getUpdate(computer.getId());
		Log log = Log.builder()
				 .typeLog(TypeLog.update)
				 .operation(operation)
				 .dateLog(LocalDateTime.now())
				 .build();
		
		logDAO.save(log);
	}
	
	public void delete(Computer computer)
	{		
		computerDAO.delete(computer);
		String operation = MessageLog.getDelete(computer.getId());
		
		Log log = Log.builder()
				 .typeLog(TypeLog.delete)
				 .operation(operation)
				 .dateLog(LocalDateTime.now())
				 .build();
		
		logDAO.save(log);
	}

	public ComputerDAO getComputerDAO() 
	{
		return computerDAO;
	}

	public void setComputerDAO(ComputerDAO computerDAO) 
	{
		this.computerDAO = computerDAO;
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
