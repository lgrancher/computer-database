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
import com.excilys.mapper.DateMapper;
import com.excilys.om.Page;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;

@Controller
@RequestMapping("/UpdateComputer")
public class UpdateComputer
{	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ComputerService computerService;
 
	@Autowired
	private ComputerValidator computerValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) 
	{
		binder.addValidators(computerValidator);
	}

	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView showForm(@RequestParam(value="id", required=false) String id, ModelMap model)
 	{
	 	ArrayList<CompanyDTO> listeCompany = (ArrayList<CompanyDTO>) companyService.retrieveAll();
		model.addAttribute("listeCompany", listeCompany);
		
		ModelAndView mav =  new ModelAndView("redirect:" +Page.urlVerifyIdExist(id, "update", computerService.lastId()));
		
		try
		{	
			Long numId = Long.parseLong(id);
			ComputerDTO computerDTO = computerService.find(numId);
			computerDTO.setIntroduced(DateMapper.formatDBVersWeb(computerDTO.getIntroduced()));
			computerDTO.setDiscontinued(DateMapper.formatDBVersWeb(computerDTO.getDiscontinued()));
				 
			if(computerDTO!=null)
			{	
				model.addAttribute("companySelect",computerDTO.getCompanyDTO().getId());
				mav = new ModelAndView("updateComputer", "computerDTO", computerDTO);
			}
		}
		
		catch(NumberFormatException e){};
		
		return mav;
    }

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView updateComputer(@ModelAttribute("computerDTO") @Valid ComputerDTO computerDTO, BindingResult result, ModelMap model) 
	{
		ModelAndView mav =  new ModelAndView("updateComputer", "computerDTO", computerDTO);
	  
		if(result.hasErrors())
		{
			ArrayList<CompanyDTO> listeCompany = (ArrayList<CompanyDTO>) companyService.retrieveAll();
			model.addAttribute("listeCompany", listeCompany);
			model.addAttribute("companySelect", computerDTO.getCompanyDTO().getId());
		}
      
		else
		{
			if(computerDTO.getName()!=null)
  		  	{
				computerService.update(computerDTO);
				mav.setViewName("redirect:index"); 
  		  	}
    	  
			else
  		  	{
				mav.setViewName("redirect:" +Page.urlVerifyIdExist(computerDTO.getId(), "update", computerService.lastId()));
  		  	}
		}
        
		return mav;
    }
}