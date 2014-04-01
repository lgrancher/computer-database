package com.excilys.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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
	protected String listeCompany(HttpServletRequest request, HttpSession session) throws ServletException, IOException 
	{		
		ArrayList<CompanyDTO> listeCompany = (ArrayList<CompanyDTO>) companyService.retrieveAll();

		session.setAttribute("listeCompany", listeCompany);
		
		return "addComputer";	
	}
	
	@RequestMapping(method = RequestMethod.POST)
	protected String addComputer(HttpServletRequest request, HttpSession session) throws ServletException, IOException 
	{
		String redirect;
		
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
			
			redirect ="addComputer";
		}
		
		else
		{
			computerService.create(computerDTO);
			
			Page<?> page = new Page<ComputerDTO>(computerService);
			page.setCurrentPage(page.getNoOfPages());
			
			session.setAttribute("page", page);
			
			redirect = "redirect: index";	
		}
		
		return redirect;
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
