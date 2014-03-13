package com.excilys.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.om.Company;
import com.excilys.om.Computer;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ComputerDAO;

/**
 * Servlet implementation class UpdateComputer
 */
@WebServlet("/FindComputer")
public class FindComputer extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String id = request.getParameter("id");
		Long numId = Long.parseLong(id);
		
		ComputerDAO computerDAO = ComputerDAO.getInstance();
		CompanyDAO companyDAO = CompanyDAO.getInstance();
		
		Computer computer;
		Company company;
		
		ArrayList<Company> listeCompany;
		
		try 
		{
			computer = computerDAO.find(numId);
			company = computer.getCompany();
			request.setAttribute("computer", computer);
			request.setAttribute("company", company);
		} 
		
		catch (SQLException e) 
		{
			request.setAttribute("computer", new Computer());
			request.setAttribute("company", new Company());
			e.printStackTrace();
		}
		
		try 
		{
			listeCompany = (ArrayList<Company>) companyDAO.retrieveAll();
			request.setAttribute("listeCompany", listeCompany);
		} 
		
		catch (SQLException e) 
		{
			request.setAttribute("listeCompany", new ArrayList<Company>());
			e.printStackTrace();
		}
		
		finally
		{
			request.getRequestDispatcher("updateComputer.jsp").forward(request, response);
		}
	}
}