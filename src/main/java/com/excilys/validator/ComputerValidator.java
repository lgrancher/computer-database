package com.excilys.validator;

import org.apache.commons.validator.DateValidator;

import com.excilys.DTO.ComputerDTO;
import com.excilys.service.ComputerService;

public class ComputerValidator
{	
	private ComputerDTO computerDTO;
	
	public ComputerValidator(ComputerDTO computerDTO)
	{
		this.computerDTO = computerDTO;	
	}
	
	// retourne un computerDTO si l'id existe, null sinon
	public ComputerDTO getComputerDTOWithId(ComputerService computerService)
	{
		try
		{	
			Long numId = Long.parseLong(computerDTO.getId());
			this.computerDTO = computerService.find(numId);
		}
		
		catch(NumberFormatException e)
		{
			this.computerDTO = null;
		}
		
		return this.computerDTO;
	}
	
	/* 
	 * retourne l'url permettant ou non une alert d'erreur
	 * si l'id a existe, avertissement comme quoi le computer a ete supprime
	 * sinon, retour a la page d'accueil, l'utilisateur a ete fouille dans l'url
	*/
	public String verifyIdExist(String id, String type, ComputerService computerService)
	{
		String url="index";
		
		try
		{
			long idComputer = Long.parseLong(id);
			long lastId = computerService.lastId();
			
			if(idComputer>0 && idComputer<=lastId)
			{
				url = "index?erreur="+idComputer+"&type="+type;
			}
		}
		
		catch(NumberFormatException e){}
		
		return url;
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
