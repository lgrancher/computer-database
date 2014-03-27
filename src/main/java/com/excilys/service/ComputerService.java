package com.excilys.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.joda.time.LocalDate;

import com.excilys.DTO.ComputerDTO;
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
	
	private ComputerService()
	{
		try 
		{
			computerDAO = ComputerDAO.getInstance();
			connectionJDBC = ConnectionJDBC.getInstance();
		}
		
		catch (SQLException e) 
		{
			e.printStackTrace();
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
		String operation;
		
		if(page.getSearch().equals(""))
		{
			operation = "Listing de tous les computers page " + page.getCurrentPage() + "/" + page.getNoOfPages();
		}
		
		else
		{
			operation = "Listing des computers avec la recherche "+page.getSearch()+" page " + page.getCurrentPage() + "/" + page.getNoOfPages();
		}
		
		Log log = Log.builder()
				 .typeLog("retrieve")
				 .operation(operation)
				 .build();
		
		Connection connection=null;
		
		try 
		{
			connection = connectionJDBC.getConnection();
			computerDAO.retrieve(page);
			
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
		
		return page;
	}
	
	public void create(ComputerDTO computerDTO) 
	{		
		Connection connection=null;
		
		try 
		{
			connection = connectionJDBC.getConnection();
			int idAdd = computerDAO.create(computerDTO);
			
			Log log = Log.builder()
					 .typeLog("create")
					 .operation("Ajout du computer "+computerDTO.getName()+", id = "+idAdd)
					 .dateLog(new LocalDate())
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
	
	public ComputerDTO find(Long id)
	{
		Log log = Log.builder()
				 .typeLog("find")
				 .operation("Recherche du computer n° "+id)
				 .build();
		
		Connection connection=null;
		ComputerDTO computerDTO=null;
		
		try 
		{
			connection = connectionJDBC.getConnection();
			computerDTO = computerDAO.find(id);
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

		return computerDTO;
	}
	
	public void update(ComputerDTO computerDTO) 
	{
		Log log = Log.builder()
				 .typeLog("update")
				 .operation("Mise à jour du computer n° "+computerDTO.getId())
				 .build();
		
		Connection connection=null;
		
		try 
		{
			connection = connectionJDBC.getConnection();
			computerDAO.update(computerDTO);
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
	
	public void delete(ComputerDTO computerDTO)
	{	
		Log log = Log.builder()
				 .typeLog("delete")
				 .operation("Suppression du computer n° "+computerDTO.getId())
				 .build();
	
		Connection connection=null;
		
		try 
		{
			connection = connectionJDBC.getConnection();
			computerDAO.delete(computerDTO);
			logDAO.create(log);
			connection.commit();
		} 
		
		catch (SQLException e) 
		{
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
	
	public int size(String search)
	{
		String operation;
		
		if(search.equals(""))
		{
			operation = "Recherche du nombre de computer en tout";
		}
		
		else
		{
			operation = "Recherche du nombre de computer correspondant à "+search;
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
			size= computerDAO.size(search);
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
