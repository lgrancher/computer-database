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

import com.excilys.DTO.ComputerDTO;
import com.excilys.DTO.PageDTO;
import com.excilys.mapper.PageMapper;
import com.excilys.om.Page;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;

public class DeleteComputer extends HttpServlet 
{	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ComputerService computerService;

	// supprime le computer
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String id = request.getParameter("id");
			
		ComputerDTO computerDTO = ComputerDTO.builder()
											 .id(id)
											 .build();		
		
		ComputerValidator computerValidator = new ComputerValidator(computerDTO);
		computerDTO = computerValidator.getComputerDTOWithId(computerService);
		
		String url = "index";
		
		if(computerDTO!=null)
		{
			computerService.delete(computerDTO);	
		}
		
		else
		{			
			url = computerValidator.verifyIdExist(id, "delete", computerService);	
		}
		
		PageDTO pageDTO = PageDTO.builder()
				 .search(request.getParameter("search"))
				 .sort(request.getParameter("sort"))
				 .currentPage(request.getParameter("currentPage"))
				 .build();

		Page<?> page = PageMapper.dtoToPage(pageDTO, computerService);

		HttpSession session = request.getSession();
		session.setAttribute("page", page);
		
		response.sendRedirect(url);
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
