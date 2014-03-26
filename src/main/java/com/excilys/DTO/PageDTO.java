package com.excilys.DTO;

public class PageDTO
{
	private String sort;
	private String search;
	private String currentPage;

	public static class Builder 
	{
       PageDTO pageDTO;
       
       private Builder() 
       {
    	   pageDTO = new PageDTO();
       }
 
       public Builder sort(String sort) 
       {
           this.pageDTO.sort = sort;
           return this;
       }
 
       public Builder search(String search) 
       {
           this.pageDTO.search = search;
           return this;
       }
       
       public Builder currentPage(String currentPage) 
       {
           this.pageDTO.currentPage = currentPage;
           return this;
       }
        
       public PageDTO build() 
       {
           return this.pageDTO;
       }
	}
	
	public PageDTO(){}

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
	
	public String getCurrentPage() 
	{
		return currentPage;
	}

	public void setCurrentPage(String currentPage) 
	{
		this.currentPage = currentPage;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((currentPage == null) ? 0 : currentPage.hashCode());
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
		PageDTO other = (PageDTO) obj;
		if (currentPage == null) {
			if (other.currentPage != null)
				return false;
		} else if (!currentPage.equals(other.currentPage))
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
		return "PageDTO [sort=" + sort + ", search=" + search
				+ ", currentPage=" + currentPage + "]";
	}
}
