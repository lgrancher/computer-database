package com.excilys.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.excilys.om.Computer;
import com.excilys.om.ComputerWrapper;
import com.excilys.om.Log;
import com.excilys.persistence.ComputerDAO;
import com.excilys.persistence.ConnectionJDBC;
import com.excilys.persistence.LogDAO;

public class ComputerService 
{
	private static ComputerService computerService;
	private static ComputerDAO computerDAO;
	private static LogDAO logDAO;
	
	private ComputerService()
	{
		computerDAO = ComputerDAO.getInstance();
		logDAO = LogDAO.getInstance();
	}
	
	public static ComputerService getInstance()
	{
		if(computerService==null)
		{
			computerService = new ComputerService();
		}
		
		return computerService;
	}
	
	public List<Computer> retrieve(ComputerWrapper computerWrapper)
	{
		String operation;
		
		if(computerWrapper.getName().equals("%"))
		{
			operation = "Listing de tous les computers";
		}
		
		else
		{
			operation = "Listing des computers avec la recherche "+computerWrapper.getName();
		}
		
		Log log = Log.builder()
				 .typeLog("retrieve")
				 .operation(operation)
				 .build();
		
		Connection connection=null;
		List<Computer> listComputer=null;
		
		try 
		{
			connection = ConnectionJDBC.getConnection();
			listComputer = computerDAO.retrieve(connection, computerWrapper);
			logDAO.create(connection, log);
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
				ConnectionJDBC.close(connection);
			}
		}
		
		return listComputer;
	}
	
	public void create(Computer computer) 
	{
		Log log = Log.builder()
					 .typeLog("create")
					 .operation("Ajout du computer "+computer.getName())
					 .dateLog(new Date())
					 .build();
		
		Connection connection=null;
		
		try 
		{
			connection = ConnectionJDBC.getConnection();
			computerDAO.create(connection, computer);
			logDAO.create(connection, log);
			connection.commit();
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
			
			try 
			{
				connection.rollback();
			} 
			
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
		}
		
		finally
		{
			if(connection!=null)
			{
				ConnectionJDBC.close(connection);
			}
		}
	}
	
	public Computer find(Long id)
	{
		Log log = Log.builder()
				 .typeLog("find")
				 .operation("Recherche du computer n° "+id)
				 .build();
		
		Connection connection=null;
		Computer computer=null;
		
		try 
		{
			connection = ConnectionJDBC.getConnection();
			computer = computerDAO.find(connection, id);
			logDAO.create(connection, log);
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
				ConnectionJDBC.close(connection);
			}
		}

		return computer;
	}
	
	public void update(Computer computer) 
	{
		Log log = Log.builder()
				 .typeLog("update")
				 .operation("Mise à jour du computer n° "+computer.getId())
				 .build();
		
		Connection connection=null;
		
		try 
		{
			connection = ConnectionJDBC.getConnection();
			computerDAO.update(connection, computer);
			logDAO.create(connection, log);
			connection.commit();
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
			try 
			{
				connection.rollback();
			} 
			
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
		}
		
		finally
		{
			if(connection!=null)
			{
				ConnectionJDBC.close(connection);
			}
		}	
	}
	
	public void delete(Computer computer)
	{	
		Log log = Log.builder()
				 .typeLog("delete")
				 .operation("Suppression du computer n° "+computer.getId())
				 .build();
	
		Connection connection=null;
		
		try 
		{
			connection = ConnectionJDBC.getConnection();
			computerDAO.delete(connection, computer);
			logDAO.create(connection, log);
			connection.commit();
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
			try 
			{
				connection.rollback();
			} 
			
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
		}
		
		finally
		{
			if(connection!=null)
			{
				ConnectionJDBC.close(connection);
			}
		}
	}
	
	public int size(String name)
	{
		String operation;
		
		if(name.equals("%"))
		{
			operation = "Recherche du nombre de computer en tout";
		}
		
		else
		{
			operation = "Recherche du nombre de computer correspondant à "+name;
		}
		
		Log log = Log.builder()
				 .typeLog("size")
				 .operation(operation)
				 .build();
		
		Connection connection=null;
		int size=0;
		
		try 
		{
			connection = ConnectionJDBC.getConnection();
			size= computerDAO.size(connection, name);
			logDAO.create(connection, log);
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
				ConnectionJDBC.close(connection);
			}
		}
		
		return size;
	}
}
