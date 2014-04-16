package com.excilys.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.excilys.DTO.ComputerDTO;
import com.excilys.mapper.DateMapper;
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
		String redirect = Page.urlVerifyIdExist(id, "delete", computerService.size(search));
		
		try
		{	
			Long numId = Long.parseLong(id);
			ComputerDTO computerDTO = computerService.find(numId);
			computerDTO.setIntroduced(DateMapper.formatDBVersWeb(computerDTO.getIntroduced()));
			computerDTO.setDiscontinued(DateMapper.formatDBVersWeb(computerDTO.getDiscontinued()));
			
			if(computerDTO!=null)
			{
				computerService.delete(computerDTO);
				redirect = "index";
			}
		}
		
		catch(NumberFormatException e){}
		
		return "redirect:"+redirect;
	}
}
