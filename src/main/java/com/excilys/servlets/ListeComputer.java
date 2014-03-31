package com.excilys.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.DTO.PageDTO;
import com.excilys.mapper.PageMapper;
import com.excilys.om.Page;
import com.excilys.service.ComputerService;

public class ListeComputer extends HttpServlet 
{
	private static final long serialVersionUID = 4062844883931660436L;
	
	@Autowired
	private ComputerService computerService;
	
	// affiche la liste des computers
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		HttpSession session = request.getSession();
		Page<?> page;
	
		// pour le changement de page
		if(request.getParameter("currentPage")!=null || 
		   request.getParameter("search")!=null ||
		   request.getParameter("sort")!=null || 
		   session.getAttribute("page")==null)
		{
			PageDTO pageDTO = PageDTO.builder()
									 .search(request.getParameter("search"))
									 .sort(request.getParameter("sort"))
									 .currentPage(request.getParameter("currentPage"))
									 .build();

			page = PageMapper.dtoToPage(pageDTO, computerService);	
		}
		
		// pour l'ajout, la modif, la suppression
		else
		{			
			page = (Page<?>) session.getAttribute("page");
		}
		
		computerService.retrieve(page);			
		
		session.setAttribute("page", page);
		session.setAttribute("erreur", request.getParameter("erreur"));
		session.setAttribute("type", request.getParameter("type"));
		request.getRequestDispatcher("WEB-INF/affichage.jsp").forward(request, response);	
	}

	public ComputerService getComputerService() 
	{
		return computerService;
	}

	public void setComputerService(ComputerService computerService) 
	{
		this.computerService = computerService;
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
	}
}
