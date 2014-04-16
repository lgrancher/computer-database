package com.excilys.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.excilys.DTO.PageDTO;
import com.excilys.mapper.PageMapper;
import com.excilys.om.Page;
import com.excilys.service.ComputerService;

@Controller
@SessionAttributes("page")
public class ListeComputer 
{	
	@Autowired
	private ComputerService computerService;

	@RequestMapping("/")
	public String home() 
	{
		return "redirect:index";
	}
	
	@RequestMapping(value="/index", method = RequestMethod.GET)
	protected String listeComputer( @RequestParam(value="search", required=false) String search, 
			   						 @RequestParam(value="sort", required=false) String sort, 
			   						 @RequestParam(value="currentPage", required=false) String currentPage,
			   						 @RequestParam(value="type", required=false) String type,
			   						 @RequestParam(value="erreur", required=false) String erreur,
			   						 ModelMap model) 
	{		
		Page<?> page;
		
		// pour le changement de page et l'ajout
		if(search!=null || sort!=null || currentPage!=null || !model.containsAttribute("page"))
		{
			PageDTO pageDTO = PageDTO.builder()
									 .search(search)
									 .sort(sort)
									 .currentPage(currentPage)
									 .build();

			page = PageMapper.dtoToPage(pageDTO, computerService.size(search));	
		}
		
		// pour la modif et la suppression
		else
		{			
			page = (Page<?>) model.get("page");
			page.update(computerService.size(search));
		}
		
		computerService.retrieve(page);			
		
		model.addAttribute("page",page);
		model.addAttribute("erreur", erreur);
		model.addAttribute("type", type);
		model.addAttribute("search",search);
		
		return "affichage";	
	}
}
