package com.excilys.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.excilys.DTO.ComputerDTO;
import com.excilys.om.Page;
import com.excilys.service.ComputerService;

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
		ComputerDTO computerDTO = new ComputerDTO(id, computerService);
		
		String redirect = "index";
		
		if(computerDTO.getName()!=null)
		{
			computerService.delete(computerDTO);	
		}
		
		else
		{			
			redirect = Page.urlVerifyIdExist(id, "delete", computerService);	
		}
		
		return "redirect:"+redirect;
	}
}
