package com.excilys.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
 * Servlet implementation class AjoutComputer
 */
@WebServlet("/AjoutComputer")
public class AjoutComputer extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String name = request.getParameter("name");
		String introduced = request.getParameter("introducedDate");
		String discontinued = request.getParameter("discontinuedDate");
		String company = request.getParameter("company");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
		
		try 
		{
			Date introducedDate = sdf.parse(introduced);
			Date discontinuedDate = sdf.parse(discontinued);
			long companyId = Long.parseLong(company);
			Company uneCompany = new Company();
			uneCompany.setId(companyId);
			
			Computer computer = new Computer();
			computer.setName(name);
			computer.setIntroduced(introducedDate);
			computer.setDiscontinued(discontinuedDate);
			computer.setCompany(uneCompany);
			
			ComputerDAO computerDAO = ComputerDAO.getInstance();
			computerDAO.create(computer);
			
			request.setAttribute("listeComputer", computerDAO.retrieveAll());
		} 
		
		catch (ParseException|NumberFormatException|SQLException e) 
		{
			e.printStackTrace();
		}
		
		finally
		{
			request.getRequestDispatcher("Affichage.jsp").forward(request, response);
		}
	}
}
