package com.excilys.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.om.Page;
import com.excilys.service.ComputerService;
import com.excilys.validator.PageValidator;

public class ListeComputer extends HttpServlet 
{
	private static final long serialVersionUID = 4062844883931660436L;

	// affiche la liste des computers
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		ComputerService computerService = ComputerService.getInstance();
		
		PageValidator pageValidator = new PageValidator(request.getParameter("currentPage"),request.getParameter("search"), request.getParameter("sort"));
	    
	    Page<?> page = Page.builder()
						   .sort(pageValidator.getSort())
						   .search(pageValidator.getSearch())
						   .offset(pageValidator.getOffset())
						   .currentPage(pageValidator.getCurrentPage())
						   .recordsPerPage(PageValidator.getRecordsPerPages())
						   .noOfRecords(pageValidator.getNoOfRecords())
						   .noOfPages(pageValidator.getNoOfPages())
						   .build();
	    		
		computerService.retrieve(page);			
		
		request.setAttribute("page", page);
		request.getRequestDispatcher("WEB-INF/affichage.jsp").forward(request, response);	
	}
}
