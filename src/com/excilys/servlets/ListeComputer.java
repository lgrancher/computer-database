package com.excilys.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.om.Computer;
import com.excilys.persistence.ComputerDAO;

public class ListeComputer extends HttpServlet 
{
	private static final long serialVersionUID = 4062844883931660436L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		try 
		{
			ComputerDAO computerDAO = ComputerDAO.getInstance();
			
			ArrayList<Computer> listeComputer = (ArrayList<Computer>) computerDAO.retrieveAll();
			int nbComputer = 0;
			
			request.setAttribute("listeComputer", listeComputer);
			request.setAttribute("nombreComputer", nbComputer);
		} 
		
		catch (SQLException e)
		{
			request.setAttribute("listeComputer", new ArrayList<Computer>());
			e.printStackTrace();
		} 
		
		finally
		{
			request.getRequestDispatcher("WEB-INF/Affichage.jsp").forward(request, response);
		}
	}
}
