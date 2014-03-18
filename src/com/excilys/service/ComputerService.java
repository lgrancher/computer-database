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
	
	public List<Computer> retrieve(ComputerWrapper computerWrapper) throws SQLException
	{
		Connection connection = ConnectionJDBC.getConnection();
				
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
	
		logDAO.create(connection, log);
		List<Computer> listComputer = computerDAO.retrieve(connection, computerWrapper);
		ConnectionJDBC.close(connection);
		
		return listComputer;
	}
	
	public void create(Computer computer) throws SQLException
	{
		Connection connection = ConnectionJDBC.getConnection();
		
		Log log = Log.builder()
					 .typeLog("create")
					 .operation("Ajout du computer "+computer.getName())
					 .dateLog(new Date())
					 .build();
		
		logDAO.create(connection, log);
		
		computerDAO.create(connection, computer);
		ConnectionJDBC.close(connection);
	}
	
	public Computer find(Long id) throws SQLException
	{
		Connection connection = ConnectionJDBC.getConnection();
		
		Log log = Log.builder()
				 .typeLog("find")
				 .operation("Recherche du computer n° "+id)
				 .build();
	
		logDAO.create(connection, log);
	
		Computer computer = computerDAO.find(connection, id);
		ConnectionJDBC.close(connection);
		
		return computer;
	}
	
	public void update(Computer computer) throws SQLException
	{
		Connection connection = ConnectionJDBC.getConnection();
		
		Log log = Log.builder()
				 .typeLog("update")
				 .operation("Mise à jour du computer n° "+computer.getId())
				 .build();
	
		logDAO.create(connection, log);
		
		computerDAO.update(connection, computer);
		ConnectionJDBC.close(connection);
	}
	
	public void delete(Computer computer) throws SQLException
	{
		Connection connection = ConnectionJDBC.getConnection();
		
		Log log = Log.builder()
				 .typeLog("delete")
				 .operation("Suppression du computer n° "+computer.getId())
				 .build();
	
		logDAO.create(connection, log);
		
		computerDAO.delete(connection, computer);
		
		ConnectionJDBC.close(connection);
	}
	
	public int size(String name) throws SQLException
	{
		Connection connection = ConnectionJDBC.getConnection();		
		
		String operation;
		
		if(name.equals("%") || name.equals(""))
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
	
		logDAO.create(connection, log);
		
		int size= computerDAO.size(connection, name);
		ConnectionJDBC.close(connection);
		
		return size;
	}
}
