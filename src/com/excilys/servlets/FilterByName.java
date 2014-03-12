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
 * Servlet implementation class FilterByName
 */
@WebServlet("/FilterByName")
public class FilterByName extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ComputerDAO computerDAO = ComputerDAO.getInstance();
		String search = request.getParameter("search");

		try 
		{
			ArrayList<Computer> listeComputers = (ArrayList<Computer>) computerDAO.find(search);
			request.setAttribute("listeComputer", listeComputers);
			
		} 
		
		catch (SQLException e) 
		{
			request.setAttribute("listeComputer", "Problème de connexion");
			e.printStackTrace();
		}
		
		finally
		{
			request.getRequestDispatcher("Affichage.jsp").forward(request, response);
		}
	}

}
