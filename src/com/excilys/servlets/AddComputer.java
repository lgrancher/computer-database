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
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ComputerDAO;

/**
 * Servlet implementation class AjoutComputer
 */
@WebServlet("/AjoutComputer")
public class AddComputer extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	// cherche la liste des company que l'utilisateur va pouvoir ajouter au computer
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
			request.getRequestDispatcher("WEB-INF/addComputer.jsp").forward(request, response);
		}
	}
	
	// ajoute le computer dans la base
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String name = request.getParameter("name");
		String introduced = request.getParameter("introducedDate");
		String discontinued = request.getParameter("discontinuedDate");
		String company = request.getParameter("company");
		
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
		
		Company uneCompany = Company.builder()
			    .id(companyId)
				.build();
		
		Computer computer = Computer.builder()
                .name(name)
                .introduced(introducedDate)
                .discontinued(discontinuedDate)
                .company(uneCompany)
                .build();
		
		ComputerDAO computerDAO = ComputerDAO.getInstance();
		
		try 
		{
			computerDAO.create(computer);
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		finally
		{
			response.sendRedirect("index");
		}
	}
}
