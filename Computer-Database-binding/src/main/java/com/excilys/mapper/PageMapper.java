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
		if(sort==null || sort.equals(""))
		{
			sort="id";
		}
		
		return sort;
	}
}
