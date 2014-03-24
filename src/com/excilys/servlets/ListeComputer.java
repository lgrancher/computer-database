package com.excilys.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.om.Page;
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
	    	search="";
	    }
	    
	    int noOfRecords = computerService.size(search); 
	    int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
	    
		if(sort==null)
		{
			sort="id";
		}
	    
	    if(request.getParameter("currentPage") != null)
	    {
	    	try
	    	{
	    		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	    		
	    		if(currentPage>noOfPages)
	    		{
	    			currentPage--;
	    		}
	    	}
	    	
	    	catch(NumberFormatException e)
	    	{
	    		currentPage = 1;
	    	}
	    }
	    
	    Page<?> page = Page.builder()
						   .sort(sort)
						   .search(search)
						   .offset((currentPage-1)*recordsPerPage)
						   .currentPage(currentPage)
						   .recordsPerPage(recordsPerPage)
						   .noOfRecords(noOfRecords)
						   .noOfPages(noOfPages)
						   .build();
	    		
		computerService.retrieve(page);			
		
		request.setAttribute("page", page);
		request.getRequestDispatcher("WEB-INF/affichage.jsp").forward(request, response);	
	}
}
