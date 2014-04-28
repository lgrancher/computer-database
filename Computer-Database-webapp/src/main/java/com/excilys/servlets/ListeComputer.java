package com.excilys.servlets;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.excilys.DTO.ComputerDTO;
import com.excilys.mapper.ComputerMapper;
import com.excilys.om.Computer;
import com.excilys.om.PageGenerator;
import com.excilys.service.ComputerService;

@Controller
@SessionAttributes("pageGen")
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
		PageGenerator pageGen;
		
		if(search!=null || sort!=null || currentPage!=null || !model.containsAttribute("pageGen"))
		{
			pageGen = new PageGenerator(search, sort, currentPage);
		}
		
		else
		{
			pageGen =  (PageGenerator) model.get("pageGen");
		}
		
		Page<Computer> page = computerService.retrieve(pageGen, search);
		
		if(page.getNumber()==page.getTotalPages())
		{
			Pageable pageable = page.previousPageable();
			pageGen.setPage(pageable);
			page = computerService.retrieve(pageGen, pageGen.getSearch());
		}
		
		List<ComputerDTO> listComputersDTO = ComputerMapper.computerToDTO(page.getContent());
		
		model.addAttribute("pageGen",pageGen);
		model.addAttribute("page",page);
		model.addAttribute("listComputersDTO", listComputersDTO);
		model.addAttribute("search", search);
		model.addAttribute("sort", sort);
		
		return "affichage";
	}
}
