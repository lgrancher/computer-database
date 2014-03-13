package com.excilys.om;

public class Company 
{
	private long id;
	private String name;
	
	 public static class Builder 
	 {
        Company company;
 
        private Builder() 
        {
            company = new Company();
        }
 
        public Builder id(Long id) 
        {
            if(id != null)
                this.company.id = id;
            return this;
        }
 
        public Builder name(String name) 
        {
            this.company.name = name;
            return this;
        }
 
        public Company build() 
        {
            return this.company;
        }
	 }
	 
	public Company() {}

	public long getId() 
	{
		return id;
	}

	public void setId(long id) 
	{
		this.id = id;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}
	
	public static Builder builder() 
	{
	   return new Builder();
	}
}
