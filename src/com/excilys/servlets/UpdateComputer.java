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
 * Servlet implementation class UpdateComputer
 */
@WebServlet("/UpdateComputer")
public class UpdateComputer extends HttpServlet
{
	private static final long serialVersionUID = 1L;
 
	// cherche la liste des company a proposer a l'utilisateur
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
			request.getRequestDispatcher("WEB-INF/updateComputer.jsp").forward(request, response);
		}
	}
	
	// modifie le computer
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String introduced = request.getParameter("introducedDate");
		String discontinued = request.getParameter("discontinuedDate");
		String company = request.getParameter("company");

		long idComputer = Long.parseLong(id);
		long companyId = Long.parseLong(company);
		
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
		
		Company uneCompany = Company.builder()
			    .id(companyId)
				.build();
		
		Computer computer = Computer.builder()
				.id(idComputer)
                .name(name)
                .introduced(introducedDate)
                .discontinued(discontinuedDate)
                .company(uneCompany)
                .build();
		
		ComputerDAO computerDAO = ComputerDAO.getInstance();
		
		try 
		{
			computerDAO.update(computer);
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
