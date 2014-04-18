package com.excilys.service;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.DTO.ComputerDTO;
import com.excilys.om.Log.Type;
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
		
	public Page<?> retrieve(Page<?> page)
	{
		computerDAO.retrieve(page);
		
		String operation = MessageLog.getRetrieve(page, false, true);
		
		Log log = Log.builder()
				 .typeLog(Type.retrieve)
				 .operation(operation)
				 .build();
		
	    logDAO.create(log);
		
		logger.info(operation);
	
		return page;
	}
	
	public void create(ComputerDTO computerDTO) 
	{			
		Long idAdd = computerDAO.create(computerDTO);
		
		String operation = MessageLog.getCreate(computerDTO.getName(), idAdd, false);
		
		Log log = Log.builder()
				 .typeLog(Type.create)
				 .operation(operation)
				 .dateLog(new LocalDate())
				 .build();
		
		logDAO.create(log);
		logger.info(operation);
	}
	
	public ComputerDTO find(Long id)
	{		
		ComputerDTO computerDTO = computerDAO.find(id);
		
		String operation = MessageLog.getFind(id, false, true);
		
		Log log = Log.builder()
				 .typeLog(Type.find)
				 .operation(operation)
				 .build();
		
		logDAO.create(log);
		logger.info(operation);

		return computerDTO;
	}
	
	public void update(ComputerDTO computerDTO) 
	{		
		computerDAO.update(computerDTO);
			
		String operation = MessageLog.getUpdate(computerDTO.getId(), false, true);
		Log log = Log.builder()
				 .typeLog(Type.update)
				 .operation(operation)
				 .build();
		
		logDAO.create(log);
		
		logger.info(operation);
	}
	
	public void delete(ComputerDTO computerDTO)
	{		
		computerDAO.delete(computerDTO);
		String operation = MessageLog.getDelete(computerDTO.getId(), false, true);
		
		Log log = Log.builder()
				 .typeLog(Type.delete)
				 .operation(operation)
				 .build();
		
		logDAO.create(log);
		logger.info(operation);
	}
	
	public int size(String search)
	{		
		int size = computerDAO.size(search);
			
		String operation = MessageLog.getSize(search, false, true);
		
		Log log = Log.builder()
				 .typeLog(Type.size)
				 .operation(operation)
				 .build();
		
		logDAO.create(log);
		logger.info(operation);
	
		return size;
	}
	
	public long lastId()
	{
		long lastId = computerDAO.lastId();
			
		String operation = MessageLog.getLastId(false, true);
		
		Log log = Log.builder()
				 .typeLog(Type.size)
				 .operation(operation)
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
