package com.excilys.om;

import java.util.Date;

public class Computer 
{
	private long id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private Company company;
	
	 public static class Builder 
	 {
        Computer computer;
 
        private Builder() 
        {
            computer = new Computer();
        }
 
        public Builder id(Long id) 
        {
            if(id != null)
                this.computer.id = id;
            return this;
        }
 
        public Builder name(String name) 
        {
            this.computer.name = name;
            return this;
        }
 
        public Builder introduced(Date introduced) 
        {
            this.computer.introduced = introduced;
            return this;
        }
 
        public Builder discontinued(Date discontinued) 
        {
            this.computer.discontinued = discontinued;
            return this;
        }
 
        public Builder company(Company company) 
        {
            this.computer.company = company;
            return this;
        }
 
        public Computer build() 
        {
            return this.computer;
        }
	 }

	public Computer(){}

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

	public Date getIntroduced() 
	{
		return introduced;
	}

	public void setIntroduced(Date introduced) 
	{
		this.introduced = introduced;
	}

	public Date getDiscontinued() 
	{
		return discontinued;
	}

	public void setDiscontinued(Date discontinued) 
	{
		this.discontinued = discontinued;
	}

	public Company getCompany() 
	{
		return company;
	}

	public void setCompany(Company company) 
	{
		this.company = company;
	}
	
	public static Builder builder() 
	{
	   return new Builder();
	}
}
