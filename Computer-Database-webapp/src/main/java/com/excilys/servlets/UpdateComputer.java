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
import com.excilys.om.Company;
import com.excilys.om.Computer;
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

	@InitBinder
	private void initBinder(WebDataBinder binder) 
	{
		binder.addValidators(new ComputerValidator());
	}

	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView showForm(@RequestParam(value="id", required=false) String id, ModelMap model)
 	{
		ArrayList<CompanyDTO> listeCompany = (ArrayList<CompanyDTO>) CompanyMapper.companyToDTO(companyService.retrieveAll());
		model.addAttribute("listeCompany", listeCompany);
		
		ModelAndView mav = null;
		
		try
		{	
			Long numId = Long.parseLong(id);
			Computer computer = computerService.find(numId);
				 
			if(computer!=null)
			{	
				Company company = computer.getCompany();
				long idCompany = 0;
				
				if(company!=null)
				{
					idCompany = company.getId();
				}
				
				model.addAttribute("companySelect", idCompany);
				mav = new ModelAndView("updateComputer", "computerDTO", ComputerMapper.computerToDTO(computer));
			}
		}
		
		catch(NumberFormatException e){};
		
		if(mav==null)
		{
			mav = new ModelAndView("redirect:" +Page.urlVerifyIdExist(id, "update", computerService.lastId()));
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
				mav.setViewName("redirect:index"); 
  		  	}
    	  
			// si le computer n'existe plus
			else
  		  	{
				mav.setViewName("redirect:" +Page.urlVerifyIdExist(computerDTO.getId(), "update", computerService.lastId()));
  		  	}
		}
        
		return mav;
    }
}
