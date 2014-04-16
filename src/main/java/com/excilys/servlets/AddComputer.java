package com.excilys.servlets;

import java.util.ArrayList;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.DTO.*;
import com.excilys.om.Page;
import com.excilys.service.*;
import com.excilys.validator.ComputerValidator;

@Controller
@RequestMapping("/AddComputer")
@SessionAttributes("companyDTO")
public class AddComputer
{
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private ComputerValidator computerValidator;

	@InitBinder()
	private void initBinder(WebDataBinder binder) 
	{
		binder.addValidators(computerValidator);
	}

	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView showForm(ModelMap model)
 	{
	 	ArrayList<CompanyDTO> listeCompany = (ArrayList<CompanyDTO>) companyService.retrieveAll();

		model.addAttribute("listeCompany", listeCompany);
		
		ModelAndView mav = new ModelAndView("addComputer", "computerDTO", new ComputerDTO());
		return mav;
    }

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView addComputer(@ModelAttribute("computerDTO") @Valid ComputerDTO computerDTO, BindingResult result, ModelMap model) 
	{  
		  ModelAndView mav =  new ModelAndView("addComputer", "computerDTO", computerDTO);
		  
	      if(result.hasErrors())
	      {	  			
	  			ArrayList<CompanyDTO> listeCompany = (ArrayList<CompanyDTO>) companyService.retrieveAll();
	  			model.addAttribute("listeCompany", listeCompany);
	    	  
	  			model.addAttribute("companySelect", computerDTO.getCompanyDTO().getId());
	      }
	      
	      else
	      {
	    	  	computerService.create(computerDTO);
	    	  	mav.setViewName("redirect:index?currentPage="+new Page<ComputerDTO>(computerService).getNoOfPages());
	      }	  
	        
	      return mav;
	}
}
