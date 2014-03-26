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
import com.excilys.om.Page;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;
import com.excilys.validator.PageValidator;

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
		PageValidator pageValidator = new PageValidator(request.getParameter("currentPage"),request.getParameter("search"), request.getParameter("sort"));
		
		String id = request.getParameter("id");
		ComputerDTO computerDTO = ComputerDTO.builder()
											 .id(id)
											 .build();
		
		ComputerValidator computerValidator = new ComputerValidator(computerDTO);
		
		if(computerValidator.verifyId())
		{
			computerDTO = ComputerService.getInstance().find(Long.parseLong(id));
			
			CompanyDTO companyDTO = computerDTO.getCompanyDTO();	
			ArrayList<CompanyDTO> listeCompany = (ArrayList<CompanyDTO>) CompanyService.getInstance().retrieveAll();				
			
			Page<?> page = Page.builder()
							   .currentPage(pageValidator.getCurrentPage())
							   .search(pageValidator.getSearch())
							   .sort(pageValidator.getSort())
							   .build();
					
			request.setAttribute("page", page);
			request.setAttribute("computerDTOold", computerDTO);
			request.setAttribute("companyDTO", companyDTO);
			request.setAttribute("listeCompany", listeCompany);
			
			request.getRequestDispatcher("WEB-INF/updateComputer.jsp").forward(request, response);
		}
		
		else
		{
			response.sendRedirect("index?currentPage="+pageValidator.getCurrentPage());	
		}
	}
	
	// modifie le computer
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String introduced = request.getParameter("introducedDate");
		String discontinued = request.getParameter("discontinuedDate");
		String idCompany = request.getParameter("company");
		
		PageValidator pageValidator = new PageValidator(request.getParameter("currentPage"),request.getParameter("search"), request.getParameter("sort"));

		int currentPage = pageValidator.getCurrentPage();
		String search = pageValidator.getSearch();
		String sort = pageValidator.getSort();
		
		CompanyDTO companyDTO = CompanyDTO.builder()
								 .id(idCompany)
								 .build();
		
		ComputerDTO computerDTOnew = ComputerDTO.builder()
											 .id(id)
											 .name(name)
											 .introduced(introduced)
											 .discontinued(discontinued)
											 .companyDTO(companyDTO)
											 .build();

		
		ComputerValidator computerValidator = new ComputerValidator(computerDTOnew);
		
		if(!computerValidator.verify())
		{			
		    ComputerDTO computerDTOold = ComputerService.getInstance().find(Long.parseLong(id));
			ArrayList<CompanyDTO> listeCompany = (ArrayList<CompanyDTO>) CompanyService.getInstance().retrieveAll();
			
			Page<?> page = Page.builder()
							   .currentPage(currentPage)
							   .search(search)
							   .sort(sort)
							   .build();
			
			request.setAttribute("computerDTOold", computerDTOold);
			request.setAttribute("computerDTOnew", computerDTOnew);
			request.setAttribute("page", page);
			request.setAttribute("verifyName", computerValidator.verifyName());
			request.setAttribute("verifyIntroduced", computerValidator.verifyIntroduced());
			request.setAttribute("verifyDiscontinued", computerValidator.verifyDiscontinued());
			request.setAttribute("listeCompany", listeCompany);
			request.getRequestDispatcher("WEB-INF/updateComputer.jsp").forward(request, response);
		}

		else
		{	
			ComputerService computerService = ComputerService.getInstance();
			computerService.update(computerDTOnew);
			
			response.sendRedirect("index?sort="+sort+"&currentPage="+currentPage+"&search="+search);	
		}
	}
}
