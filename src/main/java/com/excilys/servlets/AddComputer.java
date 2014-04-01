package com.excilys.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.DTO.CompanyDTO;
import com.excilys.DTO.ComputerDTO;
import com.excilys.om.Page;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;

@Controller
@RequestMapping("/AddComputer")
public class AddComputer
{
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ComputerService computerService;
	
	@RequestMapping(method = RequestMethod.GET)
	protected void listeCompany(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		ArrayList<CompanyDTO> listeCompany = (ArrayList<CompanyDTO>) companyService.retrieveAll();

		HttpSession session = request.getSession();
		session.setAttribute("listeCompany", listeCompany);
		
		request.getRequestDispatcher("WEB-INF/addComputer.jsp").forward(request, response);	
	}
	
	@RequestMapping(method = RequestMethod.POST)
	protected void addComputer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
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
		
		// si le computer n'est pas valide, on repropose le formulaire
		if(!computerValidator.verify())
		{
			request.setAttribute("computerDTO", computerDTO);
			request.setAttribute("verifyName", computerValidator.verifyName());
			request.setAttribute("verifyIntroduced", computerValidator.verifyIntroduced());
			request.setAttribute("verifyDiscontinued", computerValidator.verifyDiscontinued());
			
			request.getRequestDispatcher("WEB-INF/addComputer.jsp").forward(request, response);
		}
		
		else
		{
			computerService.create(computerDTO);
			
			Page<?> page = new Page<ComputerDTO>(computerService);
			page.setCurrentPage(page.getNoOfPages());
			
			HttpSession session = request.getSession();
			session.setAttribute("page", page);
			
			response.sendRedirect("index");	
		}
	}

	public CompanyService getCompanyService() 
	{
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) 
	{
		this.companyService = companyService;
	}

	public ComputerService getComputerService() 
	{
		return computerService;
	}

	public void setComputerService(ComputerService computerService) 
	{
		this.computerService = computerService;
	}
}
