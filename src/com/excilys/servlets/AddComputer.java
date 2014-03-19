package com.excilys.servlets;

import java.io.IOException;
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
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

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
		CompanyService companyService = CompanyService.getInstance();
		ArrayList<Company> listeCompany = (ArrayList<Company>) companyService.retrieveAll();

		request.setAttribute("listeCompany", listeCompany);

		request.getRequestDispatcher("WEB-INF/addComputer.jsp").forward(request, response);	
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
		
		ComputerService computerService = ComputerService.getInstance();
		computerService.create(computer);
	
		int recordsPerPage=14;		
		int noOfRecords = computerService.size("%");
		int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
		
		response.sendRedirect("index?currentPage="+noOfPages);	
	}
}
