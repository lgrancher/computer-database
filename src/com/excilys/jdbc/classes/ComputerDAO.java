package com.excilys.jdbc.classes;

import java.util.Date;

public class ComputerDAO 
{
	private int id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private String company_name;
	
	public ComputerDAO(int id, String name, Date introduced, Date discontinued, String company_name)
	{
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company_name = company_name;
	}

	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
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

	public String getCompany_name() 
	{
		return company_name;
	}

	public void setCompany_name(String company_name) 
	{
		this.company_name = company_name;
	}
	
	
}
