package com.excilys.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.excilys.DTO.ComputerDTO;
import com.excilys.DTO.PageDTO;
import com.excilys.mapper.PageMapper;
import com.excilys.om.Page;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;

@Controller
@RequestMapping("/DeleteComputer")
@SessionAttributes("page")
public class DeleteComputer
{	
	@Autowired
	private ComputerService computerService;

	@RequestMapping(method = RequestMethod.GET)
	protected String deleteComputer(@RequestParam(value="id", required=false) String id,
									 @RequestParam(value="search", required=false) String search, 
									 @RequestParam(value="sort", required=false) String sort, 
									 @RequestParam(value="currentPage", required=false) String currentPage, 
									 ModelMap model) 
	{			
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
				 .search(search)
				 .sort(sort)
				 .currentPage(currentPage)
				 .build();

		Page<?> page = PageMapper.dtoToPage(pageDTO, computerService);

		model.addAttribute("page", page);
		
		return "redirect:"+redirect;
	}
}
