package com.excilys.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.om.Computer;
import com.excilys.om.ComputerWrapper;
import com.excilys.service.ComputerService;

public class ListeComputer extends HttpServlet 
{
	private static final long serialVersionUID = 4062844883931660436L;

	// affiche la liste des computers
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		try 
		{
			String param = request.getParameter("search");
			String sort = request.getParameter("sort");
			
			if(sort==null)
			{
				sort="id";
			}
			
			int currentPage = 1;
		    int recordsPerPage = 14;
		    int noOfRecords;
		    
		    if(request.getParameter("currentPage") != null)
		    {
		    	try
		    	{
		    		currentPage = Integer.parseInt(request.getParameter("currentPage"));
		    	}
		    	
		    	catch(NumberFormatException e)
		    	{
		    		currentPage = 1;
		    	}
		    }
		    
		    ComputerService computerService = ComputerService.getInstance();

		    ComputerWrapper computerWrapper = ComputerWrapper.builder()
		    												 .sort(sort)
		    												 .name(param)
		    												 .offset((currentPage-1)*recordsPerPage)
		    												 .recordsPerPage(recordsPerPage)
		    												 .build();
		    		
			ArrayList<Computer> listeComputer;
			
			
			listeComputer = (ArrayList<Computer>) computerService.retrieve(computerWrapper);			
			noOfRecords = computerService.size(param);
		
			
			 
			int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
		    
			request.setAttribute("sort", sort);
			request.setAttribute("search", param);
			request.setAttribute("listeComputer", listeComputer);
		    request.setAttribute("noOfPages", noOfPages);
		    request.setAttribute("currentPage", currentPage);
		    request.setAttribute("noOfRecords", noOfRecords);
		} 
		
		catch (SQLException e)
		{
			request.setAttribute("listeComputer", new ArrayList<Computer>());
		} 
		
		finally
		{
			request.getRequestDispatcher("WEB-INF/affichage.jsp").forward(request, response);
		}
	}
}
