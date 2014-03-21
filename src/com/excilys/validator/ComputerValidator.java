package com.excilys.validator;

import org.apache.commons.validator.DateValidator;
import com.excilys.DTO.ComputerDTO;

public class ComputerValidator
{	
	private ComputerDTO computerDTO;
	
	public ComputerValidator(ComputerDTO computerDTO)
	{
		this.computerDTO = computerDTO;	
	}
	
	public boolean verifyName()
	{
		boolean verify = true;
		
		if(computerDTO.getName().equals(""))
		{
			verify = false;
		}
		
		return verify;
	}
	
	public boolean verifyIntroduced()
	{
		boolean vide = computerDTO.getIntroduced().equals("");
		boolean dateValide = DateValidator.getInstance().isValid(computerDTO.getIntroduced(), "yyyy-MM-dd", true);
		
		return vide || dateValide;
	}
	
	public boolean verifyDiscontinued()
	{
		boolean vide = computerDTO.getDiscontinued().equals("");
		boolean dateValide = DateValidator.getInstance().isValid(computerDTO.getDiscontinued(), "yyyy-MM-dd", true);
		boolean superieurIntroduced = computerDTO.getIntroduced().compareTo(computerDTO.getDiscontinued()) <= 0;
		
		return vide || (dateValide && superieurIntroduced);
	}
	
	public boolean verify()
	{
		return verifyName() && verifyIntroduced() && verifyDiscontinued();
	}
}
