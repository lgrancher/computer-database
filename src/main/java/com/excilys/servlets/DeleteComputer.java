package com.excilys.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.excilys.DTO.ComputerDTO;
import com.excilys.DTO.PageDTO;
import com.excilys.mapper.PageMapper;
import com.excilys.om.Page;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;

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
			
		ComputerDTO computerDTO = ComputerDTO.builder()
											 .id(id)
											 .build();		
		
		ComputerValidator computerValidator = new ComputerValidator(computerDTO);
		computerDTO = computerValidator.getComputerDTOWithId();
		
		String url = "index";
		
		if(computerDTO!=null)
		{
			ComputerService computerService = ComputerService.INSTANCE;
			computerService.delete(computerDTO);	
		}
		
		else
		{			
			url = ComputerValidator.verifyIdExist(id, "delete");	
		}
		
		PageDTO pageDTO = PageDTO.builder()
				 .search(request.getParameter("search"))
				 .sort(request.getParameter("sort"))
				 .currentPage(request.getParameter("currentPage"))
				 .build();

		Page<?> page = PageMapper.dtoToPage(pageDTO);

		HttpSession session = request.getSession();
		session.setAttribute("page", page);
		
		response.sendRedirect(url);
	}
}
