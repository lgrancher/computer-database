package com.excilys.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.DTO.ComputerDTO;
import com.excilys.service.ComputerService;

/**
 * Servlet implementation class DeleteComputer
 */
@WebServlet("/DeleteComputer")
public class DeleteComputer extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	// supprime le computer
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String id = request.getParameter("id");
		String page = request.getParameter("currentPage");
		String search = request.getParameter("search");
		String sort = request.getParameter("sort");
		
		try
		{
			Long idComputer = Long.parseLong(id);
			ComputerService computerService = ComputerService.getInstance();
			ComputerDTO computerDTO = computerService.find(idComputer);
			
			if(computerDTO!=null)
			{
				computerService.delete(computerDTO);
			}
		}
		
		catch(NumberFormatException e)
		{}
		
		int currentPage;
		
		try
		{
			currentPage = Integer.parseInt(page);
		}
		
		catch(NumberFormatException e)
		{
			currentPage=1;
		}
		
		if(search==null)
		{
			search="";
		}
		
		response.sendRedirect("index?sort="+sort+"&currentPage="+currentPage+"&search="+search);
	}
}
