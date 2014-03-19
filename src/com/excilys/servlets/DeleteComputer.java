package com.excilys.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.om.Computer;
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
		Long idComputer = Long.parseLong(id);
		
		ComputerService computerService = ComputerService.getInstance();
		Computer computer = computerService.find(idComputer);
		computerService.delete(computer);

		response.sendRedirect("index");
	}
}
