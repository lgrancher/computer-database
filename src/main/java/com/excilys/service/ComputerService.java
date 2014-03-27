package com.excilys.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.DTO.ComputerDTO;
import com.excilys.om.Log.Type;
import com.excilys.om.MessageLog;
import com.excilys.om.Page;
import com.excilys.om.Log;
import com.excilys.persistence.ComputerDAO;
import com.excilys.persistence.ConnectionJDBC;
import com.excilys.persistence.LogDAO;

public class ComputerService 
{
	private static ComputerService computerService;
	private static ComputerDAO computerDAO;
	private static LogDAO logDAO;
	private static ConnectionJDBC connectionJDBC;
	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	
	private ComputerService()
	{
		try 
		{
			computerDAO = ComputerDAO.getInstance();
			connectionJDBC = ConnectionJDBC.getInstance();
			logger.info("Instanciation du ComputerService");
		}
		
		catch (SQLException e) 
		{
			logger.error("Erreur d'instanciation du ComputerService");
		}
		
		finally
		{
			logDAO = LogDAO.getInstance();
		}
	}
	
	public static ComputerService getInstance()
	{
		if(computerService==null)
		{
			computerService = new ComputerService();
		}
		
		return computerService;
	}
	
	public Page<?> retrieve(Page<?> page)
	{
		Connection connection=null;

		try 
		{
			connection = connectionJDBC.getConnection();
			computerDAO.retrieve(page);
			
			String operation = MessageLog.getRetrieve(page, false, true);
			
			Log log = Log.builder()
					 .typeLog(Type.retrieve)
					 .operation(operation)
					 .build();
			
		    logDAO.create(log);
			connection.commit();
			
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
				
		finally
		{
			if(connection!=null)
			{
				connectionJDBC.close(connection);
			}
		}
		
		return page;
	}
	
	public void create(ComputerDTO computerDTO) 
	{		
		Connection connection=null;
		
		try 
		{
			connection = connectionJDBC.getConnection();
			Long idAdd = computerDAO.create(computerDTO);
			
			String operation = MessageLog.getCreate(computerDTO, idAdd, false);
			
			Log log = Log.builder()
					 .typeLog(Type.create)
					 .operation(operation)
					 .dateLog(new LocalDate())
					 .build();
			
			logDAO.create(log);
			connection.commit();
			logger.info(operation);
		} 
		
		catch (SQLException e) 
		{			
			try 
			{
				connection.rollback();
				
				String operation = MessageLog.getCreate(computerDTO, (long) 0, true);
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
				logger.error(MessageLog.getCreate(computerDTO, (long) 0, false));
			}
		}
		
		finally
		{
			if(connection!=null)
			{
				connectionJDBC.close(connection);
			}
		}
	}
	
	public ComputerDTO find(Long id)
	{		
		Connection connection=null;
		ComputerDTO computerDTO=null;
		
		try 
		{
			connection = connectionJDBC.getConnection();
			computerDTO = computerDAO.find(id);
			
			String operation = MessageLog.getFind(id, false, true);
			
			Log log = Log.builder()
					 .typeLog(Type.find)
					 .operation(operation)
					 .build();
			
			logDAO.create(log);
			connection.commit();
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
		
		finally
		{
			if(connection!=null)
			{
				connectionJDBC.close(connection);
			}
		}

		return computerDTO;
	}
	
	public void update(ComputerDTO computerDTO) 
	{		
		Connection connection=null;
		
		try 
		{
			connection = connectionJDBC.getConnection();
			computerDAO.update(computerDTO);
			
			String operation = MessageLog.getUpdate(computerDTO, false, true);
			Log log = Log.builder()
					 .typeLog(Type.update)
					 .operation(operation)
					 .build();
			
			logDAO.create(log);
			connection.commit();
			
			logger.info(operation);
		} 
		
		catch (SQLException e) 
		{
			try 
			{
				connection.rollback();
				String operation = MessageLog.getUpdate(computerDTO, true, true);
				logger.error(operation);

				Log log = Log.builder()
						 .typeLog(Type.error)
						 .operation(operation)
						 .build();
				
				logDAO.create(log);
			} 
			
			catch (SQLException e1) 
			{
				logger.error(MessageLog.getUpdate(computerDTO, true, false));
			}
		}
		
		finally
		{
			if(connection!=null)
			{
				connectionJDBC.close(connection);
			}
		}	
	}
	
	public void delete(ComputerDTO computerDTO)
	{		
		Connection connection=null;
		
		try 
		{
			connection = connectionJDBC.getConnection();
			computerDAO.delete(computerDTO);
			String operation = MessageLog.getDelete(computerDTO, false, true);
			
			Log log = Log.builder()
					 .typeLog(Type.delete)
					 .operation(operation)
					 .build();
			
			logDAO.create(log);
			connection.commit();
			logger.info(operation);
		} 
		
		catch (SQLException e) 
		{
			try 
			{
				connection.rollback();
				
				String operation = MessageLog.getDelete(computerDTO, true, true);
				logger.error(operation);
				
				Log log = Log.builder()
						 .typeLog(Type.error)
						 .operation(operation)
						 .build();
				
				logDAO.create(log);
			} 
			
			catch (SQLException e1) 
			{
				logger.error(MessageLog.getDelete(computerDTO, true, false));
			}
		}
		
		finally
		{
			if(connection!=null)
			{
				connectionJDBC.close(connection);
			}
		}
	}
	
	public int size(String search)
	{		
		Connection connection=null;
		int size=0;
		
		try 
		{			
			connection = connectionJDBC.getConnection();
			size= computerDAO.size(search);
			
			String operation = MessageLog.getSize(search, false, true);
			
			Log log = Log.builder()
					 .typeLog(Type.size)
					 .operation(operation)
					 .build();
			
			logDAO.create(log);
			connection.commit();
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
		
		finally
		{
			if(connection!=null)
			{
				connectionJDBC.close(connection);
			}
		}
		
		return size;
	}
}
