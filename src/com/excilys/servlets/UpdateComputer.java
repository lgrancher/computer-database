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
		int curPage;
		String sort = request.getParameter("sort");
		String search = request.getParameter("search");
		
		try
		{
			curPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		catch(NumberFormatException e)
		{
			curPage = 1;
		}
		
		String id = request.getParameter("id");
		
		try
		{	
			Long numId = Long.parseLong(id);
			
			ComputerService computerService = ComputerService.getInstance();
			CompanyService companyService = CompanyService.getInstance();
			
			ComputerDTO computerDTO = computerService.find(numId);
			
			if(computerDTO!=null)
			{
				CompanyDTO companyDTO = computerDTO.getCompanyDTO();	
				ArrayList<CompanyDTO> listeCompany = (ArrayList<CompanyDTO>) companyService.retrieveAll();				
				
				Page<?> page = Page.builder()
								   .currentPage(curPage)
								   .search(search)
								   .sort(sort)
								   .build();
						
				request.setAttribute("page", page);
				request.setAttribute("computerDTOold", computerDTO);
				request.setAttribute("companyDTO", companyDTO);
				request.setAttribute("listeCompany", listeCompany);
				
				request.getRequestDispatcher("WEB-INF/updateComputer.jsp").forward(request, response);
			}
			
			else
			{
				response.sendRedirect("index?&currentPage="+curPage);
			}
		}
		
		catch(NumberFormatException e)
		{
			response.sendRedirect("index?currentPage="+curPage);	
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
		String currentPage = request.getParameter("currentPage");
		String search = request.getParameter("search");
		String sort = request.getParameter("sort");

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

		Page<?> page = Page.builder()
						   .currentPage(Integer.parseInt(currentPage))
						   .search(search)
						   .sort(sort)
						   .build();
		
		ComputerValidator computerValidator = new ComputerValidator(computerDTOnew);
		
		if(!computerValidator.verify())
		{
			ComputerService computerService = ComputerService.getInstance();
			CompanyService companyService = CompanyService.getInstance();
			
		    ComputerDTO computerDTOold = computerService.find(Long.parseLong(id));
			ArrayList<CompanyDTO> listeCompany = (ArrayList<CompanyDTO>) companyService.retrieveAll();
			
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
