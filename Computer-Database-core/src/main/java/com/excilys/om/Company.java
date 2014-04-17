package com.excilys.om;

public class Company 
{
	private Long id;
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

	public Company(Long id) 
	{
		this.id = id;
	}
	
	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
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

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Company other = (Company) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() 
	{
		return "Company [id=" + id + ", name=" + name + "]";
	}
}
