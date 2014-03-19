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
		
		ComputerService computerService = ComputerService.getInstance();
		CompanyService companyService = CompanyService.getInstance();
		
		Computer computer;
		Company company;
		
		ArrayList<Company> listeCompany;
		
		computer = computerService.find(numId);
		company = computer.getCompany();
		request.setAttribute("computer", computer);
		request.setAttribute("company", company);
		
		
			listeCompany = (ArrayList<Company>) companyService.retrieveAll();
			request.setAttribute("listeCompany", listeCompany);
		
		
			request.setAttribute("currentPage", request.getParameter("currentPage"));
			request.getRequestDispatcher("WEB-INF/updateComputer.jsp").forward(request, response);
		
	}
	
	// modifie le computer
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String introduced = request.getParameter("introducedDate");
		String discontinued = request.getParameter("discontinuedDate");
		String company = request.getParameter("company");
		String currentPage = request.getParameter("currentPage");

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
		
		ComputerService computerService = ComputerService.getInstance();
		computerService.update(computer);
	
		response.sendRedirect("index?currentPage="+currentPage);	
	}	
}
