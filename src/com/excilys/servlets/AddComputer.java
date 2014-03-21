package com.excilys.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.DTO.ComputerDTO;
import com.excilys.mapper.ComputerMapper;
import com.excilys.om.Company;
import com.excilys.om.Computer;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;

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
		String verifyName = request.getParameter("verifyName");
		
		if(verifyName!=null)
		{
			String verifyIntroduced = request.getParameter("verifyIntroduced");
			String verifyDiscontinued = request.getParameter("verifyDiscontinued");
			
			String name = request.getParameter("name");
			String introduced = request.getParameter("introduced");
			String discontinued = request.getParameter("discontinued");
			String company = request.getParameter("company");
			
			ComputerDTO computerDTO = ComputerDTO.builder()
												 .name(name)
												 .introduced(introduced)
												 .discontinued(discontinued)
												 .idCompany(company)
												 .build();
			
			request.setAttribute("computerDTO", computerDTO);
			request.setAttribute("verifyName", verifyName);
			request.setAttribute("verifyIntroduced", verifyIntroduced);
			request.setAttribute("verifyDiscontinued", verifyDiscontinued);
		}
		
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
		
		ComputerDTO computerDTO = ComputerDTO.builder()
											 .name(name)
											 .introduced(introduced)
											 .discontinued(discontinued)
											 .idCompany(company)
											 .build();
		
		ComputerValidator computerValidator = new ComputerValidator(computerDTO);
		
		if(!computerValidator.verify())
		{
			response.sendRedirect("AddComputer?verifyName="+computerValidator.verifyName()+
											 "&verifyIntroduced="+computerValidator.verifyIntroduced()+
											 "&verifyDiscontinued="+computerValidator.verifyDiscontinued()+
											 "&name="+name+"&introduced="+introduced+"&discontinued="+discontinued+
											 "&company="+company);	
		}
		
		else
		{
			Computer computer = ComputerMapper.mapping(computerDTO);
			ComputerService computerService = ComputerService.getInstance();
			computerService.create(computer);
			
			int recordsPerPage=14;		
			int noOfRecords = computerService.size("");
			int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
			
			response.sendRedirect("index?currentPage="+noOfPages);	
		}
	}
}
