package com.excilys.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.DTO.CompanyDTO;
import com.excilys.DTO.ComputerDTO;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;
import com.excilys.validator.PageValidator;

/**
 * Servlet implementation class AjoutComputer
 */
@WebServlet("/AddComputer")
public class AddComputer extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	// cherche la liste des company que l'utilisateur va pouvoir ajouter au computer
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		CompanyService companyService = CompanyService.getInstance();
		ArrayList<CompanyDTO> listeCompany = (ArrayList<CompanyDTO>) companyService.retrieveAll();

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
		
		CompanyDTO companyDTO = CompanyDTO.builder()
										  .id(company)
										  .build();
		
		ComputerDTO computerDTO = ComputerDTO.builder()
											 .name(name)
											 .introduced(introduced)
											 .discontinued(discontinued)
											 .companyDTO(companyDTO)
											 .build();
		
		ComputerValidator computerValidator = new ComputerValidator(computerDTO);
		
		if(!computerValidator.verify())
		{
			CompanyService companyService = CompanyService.getInstance();
			ArrayList<CompanyDTO> listeCompany = (ArrayList<CompanyDTO>) companyService.retrieveAll();

			request.setAttribute("listeCompany", listeCompany);
			request.setAttribute("computerDTO", computerDTO);
			request.setAttribute("verifyName", computerValidator.verifyName());
			request.setAttribute("verifyIntroduced", computerValidator.verifyIntroduced());
			request.setAttribute("verifyDiscontinued", computerValidator.verifyDiscontinued());
			
			request.getRequestDispatcher("WEB-INF/addComputer.jsp").forward(request, response);
		}
		
		else
		{
			ComputerService computerService = ComputerService.getInstance();
			computerService.create(computerDTO);
			
			response.sendRedirect("index?currentPage="+new PageValidator().getNoOfPages());	
		}
	}
}
