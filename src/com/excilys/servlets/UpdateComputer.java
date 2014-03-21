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
import com.excilys.om.ComputerWrapper;
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
			
			Computer computer = computerService.find(numId);
			
			if(computer!=null)
			{
				Company company = computer.getCompany();	
				ArrayList<Company> listeCompany = (ArrayList<Company>) companyService.retrieveAll();
				
				
				
				ComputerWrapper computerWrapper = ComputerWrapper.builder()
																 .currentPage(curPage)
																 .search(search)
																 .sort(sort)
																 .build();
						
				request.setAttribute("computerWrapper", computerWrapper);
				request.setAttribute("computer", computer);
				request.setAttribute("company", company);
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
		String company = request.getParameter("company");
		String currentPage = request.getParameter("currentPage");
		String search = request.getParameter("search");
		String sort = request.getParameter("sort");

		ComputerDTO computerDTO = ComputerDTO.builder()
											 .id(id)
											 .name(name)
											 .introduced(introduced)
											 .discontinued(discontinued)
											 .idCompany(company)
											 .build();

		ComputerWrapper computerWrapper = ComputerWrapper.builder()
														 .currentPage(Integer.parseInt(currentPage))
														 .search(search)
														 .sort(sort)
														 .build();
		
		ComputerValidator computerValidator = new ComputerValidator(computerDTO);
		
		if(!computerValidator.verify())
		{
			ComputerService computerService = ComputerService.getInstance();
			CompanyService companyService = CompanyService.getInstance();
			
			Computer computer = computerService.find(Long.parseLong(id));
			ArrayList<Company> listeCompany = (ArrayList<Company>) companyService.retrieveAll();
			
			request.setAttribute("computer", computer);
			request.setAttribute("computerDTO", computerDTO);
			request.setAttribute("computerWrapper", computerWrapper);
			request.setAttribute("verifyName", computerValidator.verifyName());
			request.setAttribute("verifyIntroduced", computerValidator.verifyIntroduced());
			request.setAttribute("verifyDiscontinued", computerValidator.verifyDiscontinued());
			request.setAttribute("listeCompany", listeCompany);
			request.getRequestDispatcher("WEB-INF/updateComputer.jsp").forward(request, response);
		}

		else
		{	
			Computer computer = ComputerMapper.mapping(computerDTO);
			ComputerService computerService = ComputerService.getInstance();
			computerService.update(computer);
			
			response.sendRedirect("index?sort="+sort+"&currentPage="+currentPage+"&search="+search);	
		}
	}
}
