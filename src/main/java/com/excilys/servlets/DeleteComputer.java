package com.excilys.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.DTO.ComputerDTO;
import com.excilys.DTO.PageDTO;
import com.excilys.mapper.PageMapper;
import com.excilys.om.Page;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;

@Controller
@RequestMapping("/DeleteComputer")
public class DeleteComputer
{	
	@Autowired
	private ComputerService computerService;

	@RequestMapping(method = RequestMethod.GET)
	protected String deleteComputer(HttpServletRequest request, HttpSession session) throws ServletException, IOException 
	{
		String id = request.getParameter("id");
			
		ComputerDTO computerDTO = ComputerDTO.builder()
											 .id(id)
											 .build();		
		
		ComputerValidator computerValidator = new ComputerValidator(computerDTO);
		computerDTO = computerValidator.getComputerDTOWithId(computerService);
		
		String redirect = "index";
		
		if(computerDTO!=null)
		{
			computerService.delete(computerDTO);	
		}
		
		else
		{			
			redirect = computerValidator.verifyIdExist(id, "delete", computerService);	
		}
		
		PageDTO pageDTO = PageDTO.builder()
				 .search(request.getParameter("search"))
				 .sort(request.getParameter("sort"))
				 .currentPage(request.getParameter("currentPage"))
				 .build();

		Page<?> page = PageMapper.dtoToPage(pageDTO, computerService);

		session.setAttribute("page", page);
		
		return "redirect: "+redirect;
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
