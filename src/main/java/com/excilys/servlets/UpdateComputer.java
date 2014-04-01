package com.excilys.servlets;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.excilys.DTO.*;
import com.excilys.mapper.PageMapper;
import com.excilys.om.Page;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;

@Controller
@RequestMapping("/UpdateComputer")
@SessionAttributes({"page", "computerDTO", "companyDTO"})
public class UpdateComputer
{	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ComputerService computerService;
 
	@RequestMapping(method = RequestMethod.GET)
	protected String listeCompany(@RequestParam(value="id", required=false) String id, 
								   @RequestParam(value="search", required=false) String search, 
								   @RequestParam(value="sort", required=false) String sort, 
								   @RequestParam(value="currentPage", required=false) String currentPage, 
								   ModelMap model) 
	{		
		String redirect;
		
		ComputerDTO computerDTO = ComputerDTO.builder()
											 .id(id)
											 .build();
		
		ComputerValidator computerValidator = new ComputerValidator(computerDTO);
		computerDTO = computerValidator.getComputerDTOWithId(computerService);
		
		PageDTO pageDTO = PageDTO.builder()
				 .search(search)
				 .sort(sort)
				 .currentPage(currentPage)
				 .build();
		
		Page<?> page = PageMapper.dtoToPage(pageDTO, computerService);
			
		model.addAttribute("page", page);
		
		// si le computer a modifier existe
		if(computerDTO!=null)
		{			
			ArrayList<CompanyDTO> listeCompany = (ArrayList<CompanyDTO>) companyService.retrieveAll();				
			
			model.addAttribute("listeCompany", listeCompany);
			model.addAttribute("computerDTOold", computerDTO);
			
			redirect = "updateComputer";
		}
		
		else
		{
			redirect = "redirect:" +computerValidator.verifyIdExist(id, "update", computerService);
		}
		
		return redirect;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	protected String updateComputer(@RequestParam(value="id") String id,
									 @RequestParam(value="name") String name,
									 @RequestParam(value="introducedDate") String introduced,
									 @RequestParam(value="discontinuedDate") String discontinued,
									 @RequestParam(value="company") String idCompany,
									 ModelMap model) 
	{
		String redirect;
				
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
		
		// si le nouveau computer n'est pas correct, on repropose le formulaire
		if(!computerValidator.verify())
		{			
			model.addAttribute("computerDTOnew", computerDTOnew);
			model.addAttribute("verifyName", computerValidator.verifyName());
			model.addAttribute("verifyIntroduced", computerValidator.verifyIntroduced());
			model.addAttribute("verifyDiscontinued", computerValidator.verifyDiscontinued());

			redirect = "updateComputer";
		}

		// si le nouveau computer est correct
		else
		{	
			Long idComputer = Long.parseLong(id);
			
			// on verifie que le computer existe toujours
			if(computerService.find(idComputer)!=null)
			{
				computerService.update(computerDTOnew);
				redirect = "redirect:index";
			}
			 
			else
			{
				redirect = "redirect:"+computerValidator.verifyIdExist(id, "update", computerService);
			}
		}
		
		return redirect;
	}
}
