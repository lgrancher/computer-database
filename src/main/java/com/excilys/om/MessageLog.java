package com.excilys.om;

import com.excilys.DTO.ComputerDTO;

public class MessageLog 
{
	public static String getRetrieve(Page<?> page, boolean erreur, boolean log)
	{
		StringBuilder builder = new StringBuilder();
		
		if(page.getSearch().equals("") && !erreur && log)
		{
			builder.append("Listing de tous les computers page ");
			builder.append(page.getCurrentPage());
			builder.append("/");
			builder.append(page.getNoOfPages());
		}
		
		else if(!page.getSearch().equals("") && !erreur && log)
		{
			builder.append("Listing des computers avec la recherche ");
			builder.append(page.getSearch());
			builder.append(" page ");
			builder.append(page.getCurrentPage());
			builder.append("/");
			builder.append(page.getNoOfPages());
		}
		
		else if(page.getSearch().equals("") && erreur && log)
		{
			builder.append("Erreur du listing de tous les computers page ");
			builder.append(page.getCurrentPage());
			builder.append("/");
			builder.append(page.getNoOfPages());
		}
		
		else if(!page.getSearch().equals("") && erreur && log)
		{
			builder.append("Erreur du listing des computers avec la recherche ");
			builder.append(page.getSearch());
			builder.append(" page ");
			builder.append(page.getCurrentPage());
			builder.append("/");
			builder.append(page.getNoOfPages());
		}
		
		else
		{
			builder.append("Erreur de l'enregistrement du log en base lors du retrieve");
		}
		
		return builder.toString();
	}
	
	public static String getSize(String search, boolean erreur, boolean log)
	{
		StringBuilder builder = new StringBuilder();
		
		if(search.equals("") && !erreur && log)
		{
			builder.append("Recherche du nombre de computers en tout");
		}
		
		else if(!search.equals("") && !erreur && log)
		{
			builder.append("Recherche du nombre de computers correspondant à la recherche ");
			builder.append(search);
		}
		
		else if(search.equals("") && erreur && log)
		{
			builder.append("Erreur de la recherche du nombre de computers en tout ");
		}
		
		else if(!search.equals("") && erreur && log)
		{
			builder.append("Erreur de la recherche du nombre de computers correspondant à la recherche ");
			builder.append(search);
		}
		
		else
		{
			builder.append("Erreur de l'enregistrement du log en base lors du size");
		}
		
		return builder.toString();
	}

	
	public static String getCreate(ComputerDTO computerDTO, Long id, boolean rollback)
	{
		StringBuilder builder = new StringBuilder();
		
		if(id==0 && rollback)
		{
			builder.append("Erreur de l'ajout du computer ");
			builder.append(computerDTO.getName());
		}
		
		else if(id==0 && !rollback)
		{
			builder.append("Erreur du rollback de l'ajout du computer ");
			builder.append(computerDTO.getName());
		}
		
		else
		{
			builder.append("Ajout du computer ");
			builder.append(computerDTO.getName());
			builder.append(", id = ");
			builder.append(id);
		}
		
		return builder.toString();
	}
	
	public static String getFind(Long id, boolean error, boolean log)
	{
		StringBuilder builder = new StringBuilder();
		
		if(!error && log)
		{
			builder.append("Recherche du computer n° ");
			builder.append(id);
		}
		
		else if(error && log)
		{
			builder.append("Erreur de recherche sur le computer n°");
			builder.append(id);
		}
		
		else
		{
			builder.append("Erreur de l'enregistrement du log en base lors du find");
		}
		
		return builder.toString();
	}
	
	public static String getUpdate(ComputerDTO computerDTO, boolean error, boolean log)
	{
		StringBuilder builder = new StringBuilder();
		
		if(!error && log)
		{
			builder.append("Mise à jour du computer n° ");
			builder.append(computerDTO.getId());
		}
		
		else if(error && log)
		{
			builder.append("Erreur de mise à jour sur le computer n°");
			builder.append(computerDTO.getId());
		}
		
		else
		{
			builder.append("Erreur de l'enregistrement du log en base lors de l'update");
		}
		
		return builder.toString();
	}
	
	public static String getDelete(ComputerDTO computerDTO, boolean error, boolean log)
	{
		StringBuilder builder = new StringBuilder();
		
		if(!error && log)
		{
			builder.append("Suppression du computer n° ");
			builder.append(computerDTO.getId());
		}
		
		else if(error && log)
		{
			builder.append("Erreur de suppression du computer n°");
			builder.append(computerDTO.getId());
		}
		
		else
		{
			builder.append("Erreur de l'enregistrement du log en base lors du delete");
		}
		
		return builder.toString();
	}
	
	public static String getLastId(boolean erreur, boolean log)
	{
		StringBuilder builder = new StringBuilder();
		
		if(!erreur && log)
		{
			builder.append("Recherche de l'id du dernier computer");
		}
		
		else if(erreur && log)
		{
			builder.append("Erreur de la recherche de l'id du dernier computer");
		}
		
		else
		{
			builder.append("Erreur de l'enregistrement du log en base lors du lastId");
		}
		
		return builder.toString();
	}

}
