package com.excilys.om;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public class PageGenerator 
{
	private String sort;
	private String search;
	public String currentPage;
	
	
	public PageGenerator()
	{
		this.sort="id";
		this.search="";
		this.currentPage="0";
	}
	
	public PageGenerator(String search, String sort, String currentPage)
	{
		this.search = search;
		this.sort = sort;
		this.currentPage = currentPage;
	}
	
	public String retrieveSearch()
	{
		StringBuilder searchBuild = new StringBuilder("%");
		
		if(search!=null && !search.equals(""))
		{
			searchBuild.append(search);
			searchBuild.append("%");
		}
		
		return searchBuild.toString();
	}
	
	public String getSearch()
	{
		return search;
	}
	
	private int retrieveCurrentPage()
	{
		int currPage=0;
		
		if(currentPage!=null)
		{
			try
			{
				currPage = Integer.parseInt(currentPage);
			}
			
			catch(NumberFormatException e){}
		}
		
		return currPage;
	}
	
	private Sort retrieveSort()
	{
		String ord = "id";
		
		if(sort!=null && !sort.equals(""))
		{
			ord=sort;
		}
		
		List<Order> listOrders = new ArrayList<Order>();
		Order order;
		
		if(ord.equals("company"))
		{
			order = new Order(Direction.ASC, "company.name");
		}
		
		else
		{
			order = new Order(Direction.ASC, ord);
		}
		
		listOrders.add(order);
		
		if(ord.equals("introduced") || ord.equals("discontinued") || ord.equals("company"))
		{
			Order order2 = new Order(Direction.ASC, "name");
			listOrders.add(order2);
		}
		
		return new Sort(listOrders);
	}
	
	public Pageable retrievePageable()
	{
		Pageable pageable = new PageRequest(retrieveCurrentPage(), 14, retrieveSort());
		
		return pageable;
	}
	
	public void previous()
	{
		try
		{
			int page = Integer.parseInt(currentPage);
			page--;
			currentPage = page+"";
		}
		
		catch(NumberFormatException e){}
	}
}
