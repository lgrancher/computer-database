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
import com.excilys.om.Computer;
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

	@InitBinder
	private void initBinder(WebDataBinder binder) 
	{
		binder.addValidators(new ComputerValidator());
	}

	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView showForm(@RequestParam(value="id", required=false) Long id, ModelMap model)
 	{
		ArrayList<CompanyDTO> listeCompany = (ArrayList<CompanyDTO>) CompanyMapper.companyToDTO(companyService.retrieveAll());
		model.addAttribute("listeCompany", listeCompany);
		
		ModelAndView mav = new ModelAndView("redirect:index");
		
		if(id!=null)
		{
			Computer computer = computerService.find(id);
			
			if(computer!=null)
			{
				mav = new ModelAndView("updateComputer", "computerDTO", ComputerMapper.computerToDTO(computer));
			}
		}

		return mav;
    }

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView updateComputer(@ModelAttribute("computerDTO") @Valid ComputerDTO computerDTO, BindingResult result, ModelMap model) 
	{
		ModelAndView mav =  new ModelAndView("updateComputer", "computerDTO", computerDTO);
	  
		if(result.hasErrors())
		{
			ArrayList<CompanyDTO> listeCompany = (ArrayList<CompanyDTO>) CompanyMapper.companyToDTO(companyService.retrieveAll());
			model.addAttribute("listeCompany", listeCompany);
		}
      
		else
		{
			if(computerDTO.getName()!=null)
  		  	{
				computerService.update(ComputerMapper.dtoToComputer(computerDTO)); 
  		  	}
    	  
			mav.setViewName("redirect:index");
		}
        
		return mav;
    }
}
