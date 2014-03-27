package com.excilys.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.DTO.PageDTO;
import com.excilys.mapper.PageMapper;
import com.excilys.om.Page;
import com.excilys.service.ComputerService;

public class ListeComputer extends HttpServlet 
{
	private static final long serialVersionUID = 4062844883931660436L;

	// affiche la liste des computers
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		ComputerService computerService = ComputerService.INSTANCE;
		
		PageDTO pageDTO = PageDTO.builder()
								 .search(request.getParameter("search"))
								 .sort(request.getParameter("sort"))
								 .currentPage(request.getParameter("currentPage"))
								 .build();
		
		Page<?> page = PageMapper.dtoToPage(pageDTO);
		
		computerService.retrieve(page);			

		request.setAttribute("page", page);
		request.getRequestDispatcher("WEB-INF/affichage.jsp").forward(request, response);	
	}
}
