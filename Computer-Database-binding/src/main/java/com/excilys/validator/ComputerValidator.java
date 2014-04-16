package com.excilys.validator;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.validator.DateValidator;
import org.joda.time.LocalDate;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.excilys.DTO.ComputerDTO;
import com.excilys.mapper.DateMapper;

@Component
public class ComputerValidator implements Validator
{	
	public boolean verifyIntroduced(String introduced)
	{
		Locale locale = LocaleContextHolder.getLocale();
		ResourceBundle properties = ResourceBundle.getBundle("langage", locale);
		String date = properties.getString("date");

		boolean vide = introduced.equals("");
		boolean dateValide = DateValidator.getInstance().isValid(introduced, date, true);
		
		return vide || dateValide;
	}
	
	public boolean verifyDiscontinued(String introduced, String discontinued)
	{
		Locale locale = LocaleContextHolder.getLocale();
		ResourceBundle properties = ResourceBundle.getBundle("langage", locale);
		String date = properties.getString("date");
		
		boolean vide = discontinued.equals("");
	
		boolean introducedValid = DateValidator.getInstance().isValid(introduced, date, true);
		boolean discontinuedValid = DateValidator.getInstance().isValid(discontinued, date, true);
		boolean superieurIntroduced=true;
		
		if(introducedValid && discontinuedValid)
		{
			LocalDate introducedDate = new LocalDate(DateMapper.formatWebVersDB(introduced));
			LocalDate discontinuedDate = new LocalDate(DateMapper.formatWebVersDB(discontinued));
			superieurIntroduced = introducedDate.isBefore(discontinuedDate);
		}
		
		return vide || (discontinuedValid && superieurIntroduced);
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
		ValidationUtils.rejectIfEmpty(errors, "name", "errorName");	
		
		if(!verifyIntroduced(computerDTO.getIntroduced()))
		{
			errors.rejectValue("introduced", "errorIntroduced");
		}
		
		if(!verifyDiscontinued(computerDTO.getIntroduced(), computerDTO.getDiscontinued()))
		{
			errors.rejectValue("discontinued", "errorDiscontinued");
		}
	}
}
