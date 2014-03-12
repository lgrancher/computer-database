package com.excilys.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.om.Company;
import com.excilys.om.Computer;
import com.excilys.persistence.CompanyDAO;

public class ListeCompany extends HttpServlet
{
	private static final long serialVersionUID = 4860319781753851176L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try 
		{
			CompanyDAO companyDAO = CompanyDAO.getInstance();
			ArrayList<Company> listeCompany = (ArrayList<Company>) companyDAO.retrieveAll();
	
			request.setAttribute("listeCompany", listeCompany);
		} 
		
		catch (SQLException e)
		{
			request.setAttribute("listeCompany", new ArrayList<Company>());
			e.printStackTrace();
		} 
		
		finally
		{
			request.getRequestDispatcher("addComputer.jsp").forward(request, response);
		}
	}
}
