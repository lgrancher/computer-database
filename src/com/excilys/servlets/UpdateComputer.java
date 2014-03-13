package com.excilys.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.om.Company;
import com.excilys.om.Computer;
import com.excilys.persistence.ComputerDAO;

/**
 * Servlet implementation class UpdateComputer
 */
@WebServlet("/UpdateComputer")
public class UpdateComputer extends HttpServlet
{
	private static final long serialVersionUID = 1L;
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String introduced = request.getParameter("introducedDate");
		String discontinued = request.getParameter("discontinuedDate");
		String company = request.getParameter("company");
		System.out.println("company "+company);
		Long idComputer = Long.parseLong(id);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
		
		Date introducedDate;
		Date discontinuedDate;
		
		try 
		{
			introducedDate = sdf.parse(introduced);
		} 
		
		catch (ParseException e) 
		{
			introducedDate = new Date(0);
		}
		
		try
		{
			discontinuedDate = sdf.parse(discontinued);
		}
		
		catch (ParseException e) 
		{
			discontinuedDate = new Date(0);
		}
		
		long companyId = Long.parseLong(company);
		Company uneCompany = new Company();
		uneCompany.setId(companyId);
		
		Computer computer = new Computer();
		computer.setId(idComputer);
		computer.setName(name);
		computer.setIntroduced(introducedDate);
		computer.setDiscontinued(discontinuedDate);
		computer.setCompany(uneCompany);
		
		ComputerDAO computerDAO = ComputerDAO.getInstance();
		
		try 
		{
			computerDAO.update(computer);
			request.setAttribute("listeComputer", computerDAO.retrieveAll());
		} 
		
		catch (SQLException e) 
		{
			request.setAttribute("listeComputer", new ArrayList<Computer>());
			e.printStackTrace();
		}
		
		finally
		{
			request.getRequestDispatcher("Affichage.jsp").forward(request, response);
		}
	}
}