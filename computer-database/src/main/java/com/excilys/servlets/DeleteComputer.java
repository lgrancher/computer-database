package com.excilys.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.DTO.ComputerDTO;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;
import com.excilys.validator.PageValidator;

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

		if(computerValidator.verifyId())
		{
			ComputerService computerService = ComputerService.getInstance();
			computerDTO = computerService.find(Long.parseLong(id));
			computerService.delete(computerDTO);
		}
		
		PageValidator pageValidator = new PageValidator(request.getParameter("currentPage"), request.getParameter("search"), request.getParameter("sort"));
		response.sendRedirect("index?sort="+pageValidator.getSort()+"&currentPage="+pageValidator.getCurrentPage()+"&search="+pageValidator.getSearch());
	}
}
