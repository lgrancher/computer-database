package com.excilys.om;

public class ComputerWrapper 
{
	private String sort;
	private String name;
	private int offset;
	private int recordsPerPage;
	
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
 
       public Builder name(String name) 
       {
           this.computerWrapper.name = name;
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

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
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

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
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
		return "ComputerWrapper [sort=" + sort + ", name=" + name + ", offset="
				+ offset + ", recordsPerPage=" + recordsPerPage + "]";
	};
}
