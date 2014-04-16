package com.excilys.mapper;

import com.excilys.DTO.ComputerDTO;
import com.excilys.DTO.PageDTO;
import com.excilys.om.Page;

public class PageMapper 
{
	public static Page<?> dtoToPage(PageDTO pageDTO, int nbRecords)
	{
		String search = findSearch(pageDTO.getSearch());
		String sort = findSort(pageDTO.getSort());
		int currentPage = findCurrentPage(pageDTO.getCurrentPage());
		
		Page<?> page = new Page<ComputerDTO>(search, sort, currentPage, nbRecords);
		return page;
	}
	
	public static String findSearch(String search)
	{		
		if(search == null)
		{
			search = "";
		}
		
		return search;
	}
	
	public static int findCurrentPage(String curPage)
	{
		int currentPage=1;
		
		if(curPage!=null)
		{
			try
			{
				currentPage = Integer.parseInt(curPage);
			}
			
			catch(NumberFormatException e){}
		}
		
		return currentPage;
	}
	
	public static String findSort(String sort)
	{		
		if(sort==null || sort=="")
		{
			sort="id";
		}
		
		switch(sort)
		{
			case "name" :
				sort = "computer.name";
				break;
				
			case "introduced" :
				sort = "computer.introduced , computer.name";
				break;
			
			case "discontinued" :
				sort = "computer.discontinued, computer.name";
				break;
				
			case "company" :
				sort = "company.name, computer.name";
				break;
				
			case "id" :
				sort = "computer.id";	
				break;
		}
		
		return sort;
	}
}
