package com.excilys.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.om.ComputerWrapper;
import com.excilys.service.ComputerService;

public class ListeComputer extends HttpServlet 
{
	private static final long serialVersionUID = 4062844883931660436L;

	// affiche la liste des computers
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		ComputerService computerService = ComputerService.getInstance();
		String search = request.getParameter("search");
		String sort = request.getParameter("sort");
		int currentPage = 1;
	    int recordsPerPage = 14;
	    
	    if(search==null)
	    {
	    	search="%";
	    }
	    
	    int noOfRecords = computerService.size(search);
	    
		if(sort==null)
		{
			sort="id";
		}
	    
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
	    
	    ComputerWrapper computerWrapper = ComputerWrapper.builder()
	    												 .sort(sort)
	    												 .search(search)
	    												 .offset((currentPage-1)*recordsPerPage)
	    												 .currentPage(currentPage)
	    												 .recordsPerPage(recordsPerPage)
	    												 .noOfRecords(noOfRecords)
	    												 .noOfPages((int) Math.ceil(noOfRecords * 1.0 / recordsPerPage))
	    												 .build();
	    		
		computerService.retrieve(computerWrapper);			
		
		request.setAttribute("computerWrapper", computerWrapper);
		request.getRequestDispatcher("WEB-INF/affichage.jsp").forward(request, response);	
	}
}
