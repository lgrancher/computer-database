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
import com.excilys.mapper.CompanyMapper;
import com.excilys.mapper.ComputerMapper;
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

	@InitBinder()
	private void initBinder(WebDataBinder binder) 
	{
		binder.addValidators(new ComputerValidator());
	}

	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView showForm(ModelMap model)
 	{
	 	ArrayList<CompanyDTO> listeCompany = (ArrayList<CompanyDTO>) CompanyMapper.companyToDTO(companyService.retrieveAll());

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
	    	  	ArrayList<CompanyDTO> listeCompany = (ArrayList<CompanyDTO>) CompanyMapper.companyToDTO(companyService.retrieveAll());
	  			model.addAttribute("listeCompany", listeCompany);
	      }
	      
	      else
	      {
	    	  	computerService.create(ComputerMapper.dtoToComputer(computerDTO));
	    	  	
	    	  	Page<?> page = new Page<ComputerDTO>();
	    	  	page.setNoOfRecords(computerService.size(""));
	    	  	
	    	  	mav.setViewName("redirect:index?currentPage="+page.getNoOfPages());
	      }	  
	        
	      return mav;
	}
}
