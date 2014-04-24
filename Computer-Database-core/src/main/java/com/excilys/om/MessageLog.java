package com.excilys.om;

public class MessageLog 
{
	public static String getRetrieve(Page<?> page)
	{
		StringBuilder builder = new StringBuilder();
		
		if(page.getSearch().equals(""))
		{
			builder.append("Listing de tous les computers page ");
			builder.append(page.getCurrentPage());
			builder.append("/");
			builder.append(page.getNoOfPages());
		}
		
		else
		{
			builder.append("Listing des computers avec la recherche ");
			builder.append(page.getSearch());
			builder.append(" page ");
			builder.append(page.getCurrentPage());
			builder.append("/");
			builder.append(page.getNoOfPages());
		}
		
		return builder.toString();
	}
	
	public static String getSize(String search, boolean erreur, boolean log)
	{
		StringBuilder builder = new StringBuilder();
		
		if((search == null || search.equals("")) && !erreur && log)
		{
			builder.append("Recherche du nombre de computers en tout");
		}
		
		else if((search != null && !search.equals("")) && !erreur && log)
		{
			builder.append("Recherche du nombre de computers correspondant à la recherche ");
			builder.append(search);
		}
		
		else if((search == null || search.equals("")) && erreur && log)
		{
			builder.append("Erreur de la recherche du nombre de computers en tout ");
		}
		
		else if((search != null && !search.equals("")) && erreur && log)
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

	
	public static String getCreate(String name, long id)
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("Ajout du computer ");
		builder.append(name);
		builder.append(", id = ");
		builder.append(id);

		return builder.toString();
	}
	
	public static String getFind(long id)
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("Recherche du computer n° ");
		builder.append(id);
		
		return builder.toString();
	}
	
	public static String getUpdate(long id)
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("Mise à jour du computer n° ");
		builder.append(id);
		
		return builder.toString();
	}
	
	public static String getDelete(long id)
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("Suppression du computer n° ");
		builder.append(id);
		
		return builder.toString();
	}
	
	public static String getLastId()
	{
		StringBuilder builder = new StringBuilder("Recherche de l'id du dernier computer");
		
		return builder.toString();
	}

}
