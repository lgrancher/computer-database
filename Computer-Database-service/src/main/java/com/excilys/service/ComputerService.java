package com.excilys.service;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.om.Computer;
import com.excilys.om.Log.TypeLog;
import com.excilys.om.MessageLog;
import com.excilys.om.Page;
import com.excilys.om.Log;
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
	
	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
		
	public List<Computer> retrieve(Page<?> page)
	{
		List<Computer> listComputers = computerDAO.retrieve(page);
		
		String operation = MessageLog.getRetrieve(page);
		
		Log log = Log.builder()
				 .typeLog(TypeLog.retrieve)
				 .operation(operation)
				 .dateLog(LocalDateTime.now())
				 .build();
		
	    logDAO.create(log);
		
		logger.info(operation);
	
		return listComputers;
	}
	
	public void create(Computer computer) 
	{			
		Long idAdd = computerDAO.create(computer);
		
		String operation = MessageLog.getCreate(computer.getName(), idAdd);
		
		Log log = Log.builder()
				 .typeLog(TypeLog.create)
				 .operation(operation)
				 .dateLog(LocalDateTime.now())
				 .build();
		
		logDAO.create(log);
		logger.info(operation);
	}
	
	public Computer find(Long id)
	{		
		Computer computer = computerDAO.find(id);
		
		String operation = MessageLog.getFind(id);
		
		Log log = Log.builder()
				 .typeLog(TypeLog.find)
				 .operation(operation)
				 .dateLog(LocalDateTime.now())
				 .build();
		
		logDAO.create(log);
		logger.info(operation);

		return computer;
	}
	
	public void update(Computer computer) 
	{		
		computerDAO.update(computer);
			
		String operation = MessageLog.getUpdate(computer.getId());
		Log log = Log.builder()
				 .typeLog(TypeLog.update)
				 .operation(operation)
				 .dateLog(LocalDateTime.now())
				 .build();
		
		logDAO.create(log);
		
		logger.info(operation);
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
		
		logDAO.create(log);
		logger.info(operation);
	}
	
	public int size(String search)
	{		
		int size = computerDAO.size(search);
			
		String operation = MessageLog.getSize(search, false, true);
		
		Log log = Log.builder()
				 .typeLog(TypeLog.size)
				 .operation(operation)
				 .dateLog(LocalDateTime.now())
				 .build();
		
		logDAO.create(log);
		logger.info(operation);
	
		return size;
	}
	
	public long lastId()
	{
		long lastId = computerDAO.lastId();
			
		String operation = MessageLog.getLastId();
		
		Log log = Log.builder()
				 .typeLog(TypeLog.size)
				 .operation(operation)
				 .dateLog(LocalDateTime.now())
				 .build();
		
		logDAO.create(log);
		logger.info(operation);			
		
		return lastId;
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
