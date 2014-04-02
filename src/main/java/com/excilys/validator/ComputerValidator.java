package com.excilys.validator;

import org.apache.commons.validator.DateValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.excilys.DTO.ComputerDTO;

@Component
public class ComputerValidator implements Validator
{	
	public boolean verifyIntroduced(String introduced)
	{
		boolean vide = introduced.equals("");
		boolean dateValide = DateValidator.getInstance().isValid(introduced, "yyyy-MM-dd", true);
		
		return vide || dateValide;
	}
	
	public boolean verifyDiscontinued(String introduced, String discontinued)
	{
		boolean vide = discontinued.equals("");
		boolean dateValide = DateValidator.getInstance().isValid(discontinued, "yyyy-MM-dd", true);
		boolean superieurIntroduced = introduced.compareTo(discontinued) <= 0;
		
		return vide || (dateValide && superieurIntroduced);
	}
	
	@Override
	public boolean supports(Class<?> arg0) 
	{
		return ComputerDTO.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object target, Errors errors) 
	{
		ComputerDTO computerDTO = (ComputerDTO) target;
		ValidationUtils.rejectIfEmpty(errors, "name", "computerDTO.name.empty","You have not answered all required fields");	
		
		if(!verifyIntroduced(computerDTO.getIntroduced()))
		{
			errors.rejectValue("introduced", "invalid", "You have not given a correct introduced date (YYYY-MM-DD)");
		}
		
		if(!verifyDiscontinued(computerDTO.getIntroduced(), computerDTO.getDiscontinued()))
		{
			errors.rejectValue("discontinued", "invalid", "You have not given a correct discontinued date (YYYY-MM-DD and later than the introduced date)");
		}
	}
}
