package com.excilys.servlets;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.excilys.DTO.*;
import com.excilys.om.Page;
import com.excilys.service.*;
import com.excilys.validator.ComputerValidator;

@Controller
@RequestMapping("/AddComputer")
@SessionAttributes({"page", "companyDTO"})
public class AddComputer
{
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ComputerService computerService;
	
	@RequestMapping(method = RequestMethod.GET)
	protected String listeCompany(ModelMap model)  
	{		
		ArrayList<CompanyDTO> listeCompany = (ArrayList<CompanyDTO>) companyService.retrieveAll();

		model.addAttribute("listeCompany", listeCompany);
		
		return "addComputer";	
	}
	
	@RequestMapping(method = RequestMethod.POST)
	protected String addComputer(@RequestParam(value="name") String name,
			 					  @RequestParam(value="introducedDate") String introduced,
			 					  @RequestParam(value="discontinuedDate") String discontinued,
			 					  @RequestParam(value="company") String company,
			 					  ModelMap model)
	{
		String redirect;
		
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
			model.addAttribute("computerDTO", computerDTO);
			model.addAttribute("verifyName", computerValidator.verifyName());
			model.addAttribute("verifyIntroduced", computerValidator.verifyIntroduced());
			model.addAttribute("verifyDiscontinued", computerValidator.verifyDiscontinued());
			
			redirect ="addComputer";
		}
		
		else
		{
			computerService.create(computerDTO);
			
			Page<?> page = new Page<ComputerDTO>(computerService);
			page.setCurrentPage(page.getNoOfPages());
			
			model.addAttribute("page", page);
			
			redirect = "redirect:index";	
		}
		
		return redirect;
	}
}
