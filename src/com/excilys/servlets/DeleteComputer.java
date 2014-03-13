package com.excilys.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.om.Computer;
import com.excilys.persistence.ComputerDAO;

/**
 * Servlet implementation class DeleteComputer
 */
@WebServlet("/DeleteComputer")
public class DeleteComputer extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String id = request.getParameter("id");
		Long idComputer = Long.parseLong(id);
		
		ComputerDAO computerDAO = ComputerDAO.getInstance();
		Computer computer;
		try 
		{
			computer = computerDAO.find(idComputer);
			computerDAO.delete(computer);
		} 
		
		catch (SQLException e) 
		{
			
		}
		
		finally
		{
			response.sendRedirect("index");
		}
	}
}
