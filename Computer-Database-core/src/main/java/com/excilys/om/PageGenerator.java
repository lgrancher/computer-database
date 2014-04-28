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
	private Pageable page;
	private String search;
	
	public PageGenerator()
	{
		Sort sort = new Sort(getOrders("id"));
		Pageable pageRequest = new PageRequest(getCurrentPage("0"), 14, sort);
		
		this.setPage(pageRequest);
		this.setSearch("");
	}
	
	public PageGenerator(String search, String order, String currentPage)
	{
		Sort sort = new Sort(getOrders(order));
		Pageable pageRequest = new PageRequest(getCurrentPage(currentPage), 14, sort);
		
		this.setPage(pageRequest);
		this.setSearch(search);
	}
	
	private void setSearch(String search)
	{
		StringBuilder searchBuild = new StringBuilder("%");
		
		if(search!=null && !search.equals(""))
		{
			searchBuild.append(search);
			searchBuild.append("%");
		}
		
		this.search = searchBuild.toString();
	}
	
	public String getSearch()
	{
		return search;
	}
	
	private int getCurrentPage(String currentPage)
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
	
	private List<Order> getOrders(String ord)
	{
		String sort = "id";
		
		if(ord!=null && !ord.equals(""))
		{
			sort=ord;
		}
		
		List<Order> listOrders = new ArrayList<Order>();
		Order order;
		
		if(sort.equals("company"))
		{
			order = new Order(Direction.ASC, "company.name");
		}
		
		else
		{
			order = new Order(Direction.ASC, sort);
		}
		
		listOrders.add(order);
		
		if(sort.equals("introduced") || sort.equals("discontinued") || sort.equals("company"))
		{
			Order order2 = new Order(Direction.ASC, "name");
			listOrders.add(order2);
		}
		
		return listOrders;
	}

	public Pageable getPage() 
	{
		return page;
	}

	public void setPage(Pageable page) 
	{
		this.page = page;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((page == null) ? 0 : page.hashCode());
		result = prime * result + ((search == null) ? 0 : search.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageGenerator other = (PageGenerator) obj;
		if (page == null) {
			if (other.page != null)
				return false;
		} else if (!page.equals(other.page))
			return false;
		if (search == null) {
			if (other.search != null)
				return false;
		} else if (!search.equals(other.search))
			return false;
		return true;
	}

	@Override
	public String toString() 
	{
		return "PageGenerator [page=" + page + ", search=" + search + "]";
	}	
}
