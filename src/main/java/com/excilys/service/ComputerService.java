package com.excilys.service;

import java.sql.SQLException;

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
import com.excilys.persistence.ConnectionJDBC;
import com.excilys.persistence.LogDAO;

@Service
@Transactional
public class ComputerService 
{	
	@Autowired
	private ComputerDAO computerDAO;
	
	@Autowired
	private LogDAO logDAO;
	
	@Autowired
	private ConnectionJDBC connectionJDBC;
	
	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
		
	public Page<?> retrieve(Page<?> page)
	{
		try 
		{
			computerDAO.retrieve(page);
			
			String operation = MessageLog.getRetrieve(page, false, true);
			
			Log log = Log.builder()
					 .typeLog(Type.retrieve)
					 .operation(operation)
					 .build();
			
		    logDAO.create(log);
			
			logger.info(operation);
		} 
		
		catch (SQLException e) 
		{
			String operation = MessageLog.getRetrieve(page, true, true);
			
			logger.error(operation);
			
			Log log = Log.builder()
					 .typeLog(Type.error)
					 .operation(operation)
					 .build();
			
			try 
			{
				logDAO.create(log);
			} 
			
			catch (SQLException e1) 
			{
				
				logger.error(MessageLog.getRetrieve(page, true, false));
			}
			
		}
		
		return page;
	}
	
	public void create(ComputerDTO computerDTO) 
	{		
		try 
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
		
		catch (SQLException e) 
		{			
			try 
			{
				String operation = MessageLog.getCreate(computerDTO.getName(), (long) 0, true);
				logger.error(operation);
				
				Log log = Log.builder()
						 .typeLog(Type.error)
						 .operation(operation)
						 .dateLog(new LocalDate())
						 .build();
				
				logDAO.create(log);
			} 
			
			catch (SQLException e1) 
			{
				logger.error(MessageLog.getCreate(computerDTO.getName(), (long) 0, false));
			}
		}
	}
	
	public ComputerDTO find(Long id)
	{		
		ComputerDTO computerDTO=null;
		
		try 
		{
			computerDTO = computerDAO.find(id);
			
			String operation = MessageLog.getFind(id, false, true);
			
			Log log = Log.builder()
					 .typeLog(Type.find)
					 .operation(operation)
					 .build();
			
			logDAO.create(log);
			logger.info(operation);
		} 
		
		catch (SQLException e) 
		{
			String operation =  MessageLog.getFind(id, true, true);
			
			logger.error(operation);
			
			Log log = Log.builder()
					 .typeLog(Type.error)
					 .operation(operation)
					 .build();
			
			try 
			{
				logDAO.create(log);
			} 
			
			catch (SQLException e1) 
			{
				logger.error(MessageLog.getFind(id, false, false));
			}
		}

		return computerDTO;
	}
	
	public void update(ComputerDTO computerDTO) 
	{		
		try 
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
		
		catch (SQLException e) 
		{
			try 
			{
				String operation = MessageLog.getUpdate(computerDTO.getId(), true, true);
				logger.error(operation);

				Log log = Log.builder()
						 .typeLog(Type.error)
						 .operation(operation)
						 .build();
				
				logDAO.create(log);
			} 
			
			catch (SQLException e1) 
			{
				logger.error(MessageLog.getUpdate(computerDTO.getId(), true, false));
			}
		}
	}
	
	public void delete(ComputerDTO computerDTO)
	{		
		try 
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
		
		catch (SQLException e) 
		{
			try 
			{				
				String operation = MessageLog.getDelete(computerDTO.getId(), true, true);
				logger.error(operation);
				
				Log log = Log.builder()
						 .typeLog(Type.error)
						 .operation(operation)
						 .build();
				
				logDAO.create(log);
			} 
			
			catch (SQLException e1) 
			{
				logger.error(MessageLog.getDelete(computerDTO.getId(), true, false));
			}
		}
	}
	
	public int size(String search)
	{		
		int size=0;
		
		try 
		{			
			size= computerDAO.size(search);
			
			String operation = MessageLog.getSize(search, false, true);
			
			Log log = Log.builder()
					 .typeLog(Type.size)
					 .operation(operation)
					 .build();
			
			logDAO.create(log);
			logger.info(operation);
		} 
		
		catch (SQLException e) 
		{		
			String operation = MessageLog.getSize(search, true, true);
			
			logger.error(operation);
			
			Log log = Log.builder()
					 .typeLog(Type.error)
					 .operation(operation)
					 .build();
			
			try 
			{
				logDAO.create(log);
			} 
			
			catch (SQLException e1)
			{
				logger.error(MessageLog.getSize(search, true, false));
			}
			
		}		
		
		return size;
	}
	
	public long lastId()
	{
		long lastId=0;
		
		try 
		{			
			
			lastId= computerDAO.lastId();
			
			String operation = MessageLog.getLastId(false, true);
			
			Log log = Log.builder()
					 .typeLog(Type.size)
					 .operation(operation)
					 .build();
			
			logDAO.create(log);
			logger.info(operation);
		} 
		
		catch (SQLException e) 
		{		
			String operation = MessageLog.getLastId(true, true);
			
			logger.error(operation);
			
			Log log = Log.builder()
					 .typeLog(Type.error)
					 .operation(operation)
					 .build();
			
			try 
			{
				logDAO.create(log);
			} 
			
			catch (SQLException e1)
			{
				logger.error(MessageLog.getLastId(true, false));
			}
			
		}		
		
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

	public ConnectionJDBC getConnectionJDBC() 
	{
		return connectionJDBC;
	}

	public void setConnectionJDBC(ConnectionJDBC connectionJDBC) 
	{
		this.connectionJDBC = connectionJDBC;
	}
}
