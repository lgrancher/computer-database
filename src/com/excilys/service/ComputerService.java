package com.excilys.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.excilys.om.Computer;
import com.excilys.persistence.ComputerDAO;
import com.excilys.persistence.ConnectionJDBC;

public class ComputerService 
{
	private static ComputerService computerService;
	private static ComputerDAO computerDAO;
	
	private ComputerService()
	{
		computerDAO = ComputerDAO.getInstance();
	}
	
	public static ComputerService getInstance()
	{
		if(computerService==null)
		{
			computerService = new ComputerService();
		}
		
		return computerService;
	}
	
	public List<Computer> retrieveAll(String sort, int offset, int noOfRecords) throws SQLException
	{
		Connection connection = ConnectionJDBC.getConnection();
		List<Computer> listComputer = computerDAO.retrieveAll(connection, sort, offset, noOfRecords);
		ConnectionJDBC.close(connection);
		
		return listComputer;
	}
	
	public void create(Computer computer) throws SQLException
	{
		Connection connection = ConnectionJDBC.getConnection();
		computerDAO.create(connection, computer);
		ConnectionJDBC.close(connection);
	}
	
	public List<Computer> find(String sort, String name, int offset, int noOfRecords) throws SQLException
	{
		Connection connection = ConnectionJDBC.getConnection();
		List<Computer> listComputer = computerDAO.find(connection, sort, name, offset, noOfRecords);
		ConnectionJDBC.close(connection);
		
		return listComputer;
	}
	
	public Computer find(Long id) throws SQLException
	{
		Connection connection = ConnectionJDBC.getConnection();
		Computer computer = computerDAO.find(connection, id);
		ConnectionJDBC.close(connection);
		
		return computer;
	}
	
	public void update(Computer computer) throws SQLException
	{
		Connection connection = ConnectionJDBC.getConnection();
		computerDAO.update(connection, computer);
		ConnectionJDBC.close(connection);
	}
	
	public void delete(Computer computer) throws SQLException
	{
		Connection connection = ConnectionJDBC.getConnection();
		computerDAO.delete(connection, computer);
		ConnectionJDBC.close(connection);
	}
	
	public int sizeAll() throws SQLException
	{
		Connection connection = ConnectionJDBC.getConnection();
		int size= computerDAO.sizeAll(connection);
		ConnectionJDBC.close(connection);
		
		return size;
	}
	
	public int size(String name) throws SQLException
	{
		Connection connection = ConnectionJDBC.getConnection();
		int size= computerDAO.size(connection, name);
		ConnectionJDBC.close(connection);
		
		return size;
	}
}
