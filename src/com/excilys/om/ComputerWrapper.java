package com.excilys.om;


import java.util.List;

public class ComputerWrapper 
{
	private String sort;
	private String search;
	private int offset;
	private int recordsPerPage;
	private int currentPage;
	private int noOfPages;
	private int noOfRecords;
	private List<Computer> listeComputer;
	
	public static class Builder 
	{
       ComputerWrapper computerWrapper;
       
       private Builder() 
       {
           computerWrapper = new ComputerWrapper();
       }
 
       public Builder sort(String sort) 
       {
           this.computerWrapper.sort = sort;
           return this;
       }
 
       public Builder search(String search) 
       {
           this.computerWrapper.search = search;
           return this;
       }
       
       public Builder offset(int offset) 
       {
           this.computerWrapper.offset = offset;
           return this;
       }
       
       public Builder recordsPerPage(int recordsPerPage) 
       {
           this.computerWrapper.recordsPerPage = recordsPerPage;
           return this;
       }
       
       public Builder currentPage(int currentPage) 
       {
           this.computerWrapper.currentPage = currentPage;
           return this;
       }
       
       public Builder noOfPages(int noOfPages) 
       {
           this.computerWrapper.noOfPages = noOfPages;
           return this;
       }
       
       public Builder noOfRecords(int noOfRecords) 
       {
           this.computerWrapper.noOfRecords = noOfRecords;
           return this;
       }
       
       
       public Builder listeComputer(List<Computer> listeComputer)
       {
    	   this.computerWrapper.setListeComputer(listeComputer);
    	   return this;
       }
 
       public ComputerWrapper build() 
       {
           return this.computerWrapper;
       }
	}
	
	public ComputerWrapper(){}

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

	public int getOffset() 
	{
		return offset;
	}

	public void setOffset(int offset) 
	{
		this.offset = offset;
	}

	public int getRecordsPerPage() 
	{
		return recordsPerPage;
	}

	public void setRecordsPerPage(int recordsPerPage) 
	{
		this.recordsPerPage = recordsPerPage;
	}
	
	public int getCurrentPage() 
	{
		return currentPage;
	}

	public void setCurrentPage(int currentPage) 
	{
		this.currentPage = currentPage;
	}

	public List<Computer> getListeComputer() 
	{
		return listeComputer;
	}

	public void setListeComputer(List<Computer> listeComputer) 
	{
		this.listeComputer = listeComputer;
	}

	public int getNoOfPages() 
	{
		return noOfPages;
	}

	public void setNoOfPages(int noOfPages) 
	{
		this.noOfPages = noOfPages;
	}

	public int getNoOfRecords() {
		return noOfRecords;
	}

	public void setNoOfRecords(int noOfRecords)
	{
		this.noOfRecords = noOfRecords;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((search == null) ? 0 : search.hashCode());
		result = prime * result + recordsPerPage;
		result = prime * result + offset;
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
		ComputerWrapper other = (ComputerWrapper) obj;
		if (search == null) {
			if (other.search != null)
				return false;
		} else if (!search.equals(other.search))
			return false;
		if (recordsPerPage != other.recordsPerPage)
			return false;
		if (offset != other.offset)
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
		return "ComputerWrapper [sort=" + sort + ", search=" + search + ", offset="
				+ offset + ", recordsPerPage=" + recordsPerPage
				+ ", currentPage=" + currentPage + ", noOfPages=" + noOfPages
				+ ", noOfRecords=" + noOfRecords + ", listeComputer="
				+ listeComputer + "]";
	}
}
