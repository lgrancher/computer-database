package com.excilys.mapper;

import com.excilys.DTO.PageDTO;
import com.excilys.om.Page;

public class PageMapper 
{
	public static Page<?> dtoToPage(PageDTO pageDTO)
	{
		String search = findSearch(pageDTO.getSearch());
		String sort = findSort(pageDTO.getSort());
		
		Page<?> page = Page.builder()
						   .search(search)
						   .sort(sort)
						   .build();
		
		int currentPage = findCurrentPage(pageDTO.getCurrentPage(), page);
		page.setCurrentPage(currentPage);

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
	
	public static int findCurrentPage(String curPage, Page<?> page)
	{
		int currentPage=1;
		
		if(curPage!=null)
		{
			try
			{
				currentPage = Integer.parseInt(curPage);
				
				if(currentPage>page.getNoOfPages())
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
