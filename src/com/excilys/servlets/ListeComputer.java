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

	// affiche la liste des computers
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		try 
		{
			ComputerDAO computerDAO = ComputerDAO.getInstance();
			
			String param = request.getParameter("search");
			
			int currentPage = 1;
		    int recordsPerPage = 15;
		    int noOfRecords;
		    
		    if(request.getParameter("currentPage") != null)
		    {
		    	currentPage = Integer.parseInt(request.getParameter("currentPage"));
		    }

			ArrayList<Computer> listeComputer;
			
			// tous les computers
			if(param==null)
			{
				listeComputer = (ArrayList<Computer>) computerDAO.retrieveAll((currentPage-1)*recordsPerPage, recordsPerPage);			
				noOfRecords = computerDAO.sizeAll();
			}
			
			// filter by name
			else
			{
				listeComputer = (ArrayList<Computer>) computerDAO.find(param, (currentPage-1)*recordsPerPage, recordsPerPage);
				noOfRecords = computerDAO.size(param); 
			}
			 
			int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
		    
			request.setAttribute("search", param);
			request.setAttribute("listeComputer", listeComputer);
		    request.setAttribute("noOfPages", noOfPages);
		    request.setAttribute("currentPage", currentPage);
		    request.setAttribute("noOfRecords", noOfRecords);
		} 
		
		catch (SQLException e)
		{
			request.setAttribute("listeComputer", new ArrayList<Computer>());
			e.printStackTrace();
		} 
		
		finally
		{
			request.getRequestDispatcher("WEB-INF/affichage.jsp").forward(request, response);
		}
	}
}
