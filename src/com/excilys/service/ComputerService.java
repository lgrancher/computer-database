package com.excilys.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

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
	private static ConnectionJDBC connectionJDBC;
	
	private ComputerService()
	{
		try 
		{
			computerDAO = ComputerDAO.getInstance();
		}
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		connectionJDBC = ConnectionJDBC.getInstance();
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
	
	public ComputerWrapper retrieve(ComputerWrapper computerWrapper)
	{
		String operation;
		
		if(computerWrapper.getSearch().equals("%"))
		{
			operation = "Listing de tous les computers page " + computerWrapper.getCurrentPage() + "/" + computerWrapper.getNoOfPages();
		}
		
		else
		{
			operation = "Listing des computers avec la recherche "+computerWrapper.getSearch()+" page " + computerWrapper.getCurrentPage() + "/" + computerWrapper.getNoOfPages();
		}
		
		Log log = Log.builder()
				 .typeLog("retrieve")
				 .operation(operation)
				 .build();
		
		Connection connection=null;
		
		try 
		{
			connection = connectionJDBC.getConnection();
			computerWrapper.setListeComputer(computerDAO.retrieve(computerWrapper));
			
		    logDAO.create(log);
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
				connectionJDBC.close(connection);
			}
		}
		
		return computerWrapper;
	}
	
	public void create(Computer computer) 
	{		
		Connection connection=null;
		
		try 
		{
			connection = connectionJDBC.getConnection();
			int idAdd = computerDAO.create(computer);
			
			Log log = Log.builder()
					 .typeLog("create")
					 .operation("Ajout du computer "+computer.getName()+", id = "+idAdd)
					 .dateLog(new Date())
					 .build();
			
			logDAO.create(log);
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
				connectionJDBC.close(connection);
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
			connection = connectionJDBC.getConnection();
			computer = computerDAO.find(id);
			logDAO.create(log);
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
				connectionJDBC.close(connection);
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
			connection = connectionJDBC.getConnection();
			computerDAO.update(computer);
			logDAO.create(log);
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
				connectionJDBC.close(connection);
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
			connection = connectionJDBC.getConnection();
			computerDAO.delete(computer);
			logDAO.create(log);
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
				connectionJDBC.close(connection);
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
			connection = connectionJDBC.getConnection();
			size= computerDAO.size(name);
			logDAO.create(log);
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
				connectionJDBC.close(connection);
			}
		}
		
		return size;
	}
}
