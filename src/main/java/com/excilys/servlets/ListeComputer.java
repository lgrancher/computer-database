package com.excilys.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.DTO.PageDTO;
import com.excilys.mapper.PageMapper;
import com.excilys.om.Page;
import com.excilys.service.ComputerService;

@Controller
public class ListeComputer 
{	
	@Autowired
	private ComputerService computerService;
	
	
	@RequestMapping("/")
	public String home() 
	{
		return "redirect: index";
	}
	
	@RequestMapping(value="/index", method = RequestMethod.GET)
	protected String listeComputer(HttpServletRequest request, HttpSession session) throws ServletException, IOException
	{	
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
		
		return "affichage";	
	}

	public ComputerService getComputerService() 
	{
		return computerService;
	}

	public void setComputerService(ComputerService computerService) 
	{
		this.computerService = computerService;
	}
}
