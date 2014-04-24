package com.excilys.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.excilys.om.Computer;
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
		String redirect = null;
		
		try
		{	
			Long numId = Long.parseLong(id);
			Computer computer = computerService.find(numId);
			
			if(computer!=null)
			{
				computerService.delete(computer);
				redirect = "index";
			}
		}
		
		catch(NumberFormatException e){}
		
		if(redirect==null)
		{
			redirect = Page.urlVerifyIdExist(id, "delete", computerService.lastId());
		}
		
		return "redirect:"+redirect;
	}
}
