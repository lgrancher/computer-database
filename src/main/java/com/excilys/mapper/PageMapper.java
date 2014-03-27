package com.excilys.mapper;

import com.excilys.DTO.PageDTO;
import com.excilys.om.Page;

public class PageMapper 
{
	public static Page<?> dtoToPage(PageDTO pageDTO)
	{
		String search = findSearch(pageDTO.getSearch());
		int currentPage = findCurrentPage(pageDTO.getCurrentPage(), search);
		String sort = findSort(pageDTO.getSort());
		
		Page<?> page = Page.builder()
						   .search(search)
						   .currentPage(currentPage)
						   .sort(sort)
						   .build();
		
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
	
	public static int findCurrentPage(String page, String search)
	{
		int currentPage=1;
		
		if(page!=null)
		{
			try
			{
				currentPage = Integer.parseInt(page);
				
				if(currentPage>Page.calculNoOfPages(search))
	    		{
					currentPage--;
	    		}
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
				sort = "computer.introduced";
				break;
			
			case "discontinued" :
				sort = "computer.discontinued";
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
