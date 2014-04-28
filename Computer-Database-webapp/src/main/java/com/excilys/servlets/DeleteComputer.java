package com.excilys.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.excilys.om.Computer;
import com.excilys.service.ComputerService;

@Controller
@RequestMapping("/DeleteComputer")
public class DeleteComputer
{	
	@Autowired
	private ComputerService computerService;

	@RequestMapping(method = RequestMethod.GET)
	protected String deleteComputer(@RequestParam(value="id", required=false) long id,
									 @RequestParam(value="search", required=false) String search, 
									 @RequestParam(value="sort", required=false) String sort, 
									 @RequestParam(value="currentPage", required=false) String currentPage, 
									 ModelMap model) 
	{			
		Computer computer = computerService.find(id);
		
		if(computer!=null)
		{
			computerService.delete(computer);
		}
		
		return "redirect:index";
	}
}
