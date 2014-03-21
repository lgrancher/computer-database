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
		return DateValidator.getInstance().isValid(computerDTO.getIntroduced(), "yyyy-MM-dd", true) ||
				computerDTO.getIntroduced().equals(""); 
	}
	
	public boolean verifyDiscontinued()
	{
		return DateValidator.getInstance().isValid(computerDTO.getDiscontinued(), "yyyy-MM-dd", true) ||
				computerDTO.getDiscontinued().equals(""); 
	}
	
	public boolean verify()
	{
		return verifyName() && verifyIntroduced() && verifyDiscontinued();
	}
}
