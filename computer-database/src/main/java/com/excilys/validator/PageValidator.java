package com.excilys.validator;

import com.excilys.service.ComputerService;

public class PageValidator 
{
	private final static int RECORDS_PER_PAGES = 14;
	private String search;
	private String sort;
	private int currentPage;
	
	public PageValidator()
	{
		search = "";
		currentPage = 1;
		sort = "id";
	}
	
	public PageValidator(String currentPage, String search, String sort)
	{
		if(search == null)
		{
			search = "";
		}
		
		this.search = search;
		
		int page = 1;
		
		if(currentPage!=null)
		{
			try
			{
				page = Integer.parseInt(currentPage);
				
				if(page>getNoOfPages())
	    		{
	    			page--;
	    		}
			}
			
			catch(NumberFormatException e){}
		}

		this.currentPage = page;
		
		if(sort == null)
		{
			sort = "id";
		}
		
		this.sort = sort;
	}
	
	public String getSearch()
	{
		return search;
	}
	
	public int getCurrentPage()
	{
		return currentPage;
	}
	
	public String getSort()
	{		
		return sort;
	}
	
	public int getNoOfPages()
	{
		return (int) Math.ceil(getNoOfRecords() * 1.0 / getRecordsPerPages() );
	}
	
	public int getNoOfRecords()
	{
		return ComputerService.getInstance().size(search); 
	}
	
	public int getOffset()
	{
		return (currentPage-1)*getRecordsPerPages();
	}

	public static int getRecordsPerPages() 
	{
		return RECORDS_PER_PAGES;
	}
}
