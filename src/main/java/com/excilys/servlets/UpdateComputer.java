package com.excilys.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.DTO.CompanyDTO;
import com.excilys.DTO.ComputerDTO;
import com.excilys.DTO.PageDTO;
import com.excilys.mapper.PageMapper;
import com.excilys.om.Page;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;


public class UpdateComputer extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ComputerService computerService;
 
	// cherche la liste des company a proposer a l'utilisateur
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		String id = request.getParameter("id");
		ComputerDTO computerDTO = ComputerDTO.builder()
											 .id(id)
											 .build();
		
		ComputerValidator computerValidator = new ComputerValidator(computerDTO);
		computerDTO = computerValidator.getComputerDTOWithId(computerService);
		
		PageDTO pageDTO = PageDTO.builder()
				 .search(request.getParameter("search"))
				 .sort(request.getParameter("sort"))
				 .currentPage(request.getParameter("currentPage"))
				 .build();
		
		Page<?> page = PageMapper.dtoToPage(pageDTO, computerService);
			
		HttpSession session = request.getSession();
		session.setAttribute("page", page);
		
		// si le computer a modifier existe
		if(computerDTO!=null)
		{			
			ArrayList<CompanyDTO> listeCompany = (ArrayList<CompanyDTO>) companyService.retrieveAll();				
			
			session.setAttribute("listeCompany", listeCompany);
			session.setAttribute("computerDTOold", computerDTO);
			
			request.getRequestDispatcher("WEB-INF/updateComputer.jsp").forward(request, response);
		}
		
		else
		{
			response.sendRedirect(computerValidator.verifyIdExist(id, "update", computerService));
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

		PageDTO pageDTO = PageDTO.builder()
				 .search(request.getParameter("search"))
				 .sort(request.getParameter("sort"))
				 .currentPage(request.getParameter("currentPage"))
				 .build();
		
		Page<?> page = PageMapper.dtoToPage(pageDTO, computerService);
			
		HttpSession session = request.getSession();
		session.setAttribute("page", page);
		
		// si le nouveau computer n'est pas correct, on repropose le formulaire
		if(!computerValidator.verify())
		{			
			request.setAttribute("computerDTOnew", computerDTOnew);
			request.setAttribute("verifyName", computerValidator.verifyName());
			request.setAttribute("verifyIntroduced", computerValidator.verifyIntroduced());
			request.setAttribute("verifyDiscontinued", computerValidator.verifyDiscontinued());

			request.getRequestDispatcher("WEB-INF/updateComputer.jsp").forward(request, response);
		}

		// si le nouveau computer est correct
		else
		{	
			Long idComputer = Long.parseLong(id);
			
			// on verifie que le computer existe toujours
			if(computerService.find(idComputer)!=null)
			{
				computerService.update(computerDTOnew);
				response.sendRedirect("index");
			}
			 
			else
			{
				response.sendRedirect(computerValidator.verifyIdExist(id, "update", computerService));
			}
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
	
	@Override
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
	}
}
