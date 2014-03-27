package com.excilys.om;

import java.util.List;

import com.excilys.service.ComputerService;

public class Page<T>
{
	private String sort;
	private String search;
	private int currentPage;
	private int noOfPages;
	private int offset;
	private int noOfRecords;
	private List<?> listeElements;
	private final static int RECORDS_PER_PAGES = 14;
	
	public static class Builder 
	{
       Page<?> page;
       
       private Builder() 
       {
    	   page = new Page<>();
       }
 
       public Builder sort(String sort) 
       {
           this.page.sort = sort;
           return this;
       }
 
       public Builder search(String search) 
       {
           this.page.search = search;
           return this;
       }
       
       public Builder currentPage(int currentPage) 
       {
           this.page.currentPage = currentPage;
           return this;
       }       
       
       public Builder listeElements(List<?> listeElements)
       {
    	   this.page.setListeElements(listeElements);
    	   return this;
       }
 
       public Page<?> build() 
       {
    	   this.page.getNoOfRecords();
    	   this.page.getNoOfPages();
    	   this.page.getOffset();
    	   
           return this.page;
       }
	}
	
	public Page(){}

	public static Builder builder() 
	{
	   return new Builder();
	}
	
	public String getSort() 
	{
		return sort;
	}

	public void setSort(String sort) 
	{
		this.sort = sort;
	}

	public String getSearch() 
	{
		return search;
	}

	public void setSearch(String search) 
	{
		this.search = search;
	}
	
	public int getCurrentPage() 
	{
		return currentPage;
	}

	public void setCurrentPage(int currentPage) 
	{
		this.currentPage = currentPage;
		getOffset();
	}

	public List<?> getListeElements() 
	{
		return listeElements;
	}

	public void setListeElements(List<?> listeElements2) 
	{
		this.listeElements = listeElements2;
	}

	public int getNoOfPages()
	{
		if(noOfPages==0)
		{
			noOfPages = (int) Math.ceil(getNoOfRecords() * 1.0 / getRecordsPerPages() );
		}
		
		return noOfPages;
	}
		
	public int getNoOfRecords()
	{
		if(noOfRecords==0)
		{
			noOfRecords = ComputerService.getInstance().size(search); 
		}
		
		return noOfRecords; 
	}
	
	public int getOffset()
	{
		if(offset<=0)
		{
			offset = (currentPage-1)*getRecordsPerPages();
		}
		
		return offset;
	}
	
	public static int getRecordsPerPages() 
	{
		return RECORDS_PER_PAGES;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + currentPage;
		result = prime * result
				+ ((listeElements == null) ? 0 : listeElements.hashCode());
		result = prime * result + noOfPages;
		result = prime * result + noOfRecords;
		result = prime * result + offset;
		result = prime * result + ((search == null) ? 0 : search.hashCode());
		result = prime * result + ((sort == null) ? 0 : sort.hashCode());
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
		Page<?> other = (Page<?>) obj;
		if (currentPage != other.currentPage)
			return false;
		if (listeElements == null) {
			if (other.listeElements != null)
				return false;
		} else if (!listeElements.equals(other.listeElements))
			return false;
		if (noOfPages != other.noOfPages)
			return false;
		if (noOfRecords != other.noOfRecords)
			return false;
		if (offset != other.offset)
			return false;
		if (search == null) {
			if (other.search != null)
				return false;
		} else if (!search.equals(other.search))
			return false;
		if (sort == null) {
			if (other.sort != null)
				return false;
		} else if (!sort.equals(other.sort))
			return false;
		return true;
	}

	@Override
	public String toString() 
	{
		return "Page [sort=" + sort + ", search=" + search + ", currentPage="
				+ currentPage + ", noOfPages=" + noOfPages + ", offset="
				+ offset + ", noOfRecords=" + noOfRecords + ", listeElements="
				+ listeElements + "]";
	}
}
