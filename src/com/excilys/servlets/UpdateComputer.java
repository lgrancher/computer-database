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
		String id = request.getParameter("id");
		Long numId = Long.parseLong(id);

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
		
		ComputerService computerService = ComputerService.getInstance();
		CompanyService companyService = CompanyService.getInstance();
		
		Computer computer = computerService.find(numId);
		Company company = computer.getCompany();	
		ArrayList<Company> listeCompany = (ArrayList<Company>) companyService.retrieveAll();
	
		int curPage;
		
		try
		{
			curPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		catch(NumberFormatException e)
		{
			curPage = 1;
		}
		
		ComputerWrapper computerWrapper = ComputerWrapper.builder()
														 .currentPage(curPage)
														 .search(request.getParameter("search"))
														 .sort(request.getParameter("sort"))
														 .build();
				
		request.setAttribute("computerWrapper", computerWrapper);
		request.setAttribute("computer", computer);
		request.setAttribute("company", company);
		request.setAttribute("listeCompany", listeCompany);
		
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
		String search = request.getParameter("search");
		String sort = request.getParameter("sort");

		ComputerDTO computerDTO = ComputerDTO.builder()
											 .id(id)
											 .name(name)
											 .introduced(introduced)
											 .discontinued(discontinued)
											 .idCompany(company)
											 .build();

		ComputerValidator computerValidator = new ComputerValidator(computerDTO);
		
		if(!computerValidator.verify())
		{
			response.sendRedirect("UpdateComputer?verifyName="+computerValidator.verifyName()+
						 "&verifyIntroduced="+computerValidator.verifyIntroduced()+
						 "&verifyDiscontinued="+computerValidator.verifyDiscontinued()+"&id="+id+
						 "&name="+name+"&introduced="+introduced+"&discontinued="+discontinued+
						 "&company="+company+"&sort="+sort+"&currentPage="+currentPage+"&search="+search);	
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
