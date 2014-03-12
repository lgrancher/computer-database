package com.excilys.om;

import java.util.Date;

public class Computer 
{
	private long id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private Company company;
	
	public Computer()
	{

	}

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
	
	
}
