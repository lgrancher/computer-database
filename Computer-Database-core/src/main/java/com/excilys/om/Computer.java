package com.excilys.om;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
@Table(name = "computer")
public class Computer 
{
	@Id   @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="introduced")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate introduced;
	
	@Column(name="discontinued")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate discontinued;
	
	@JoinColumn(name="company_id")
	@ManyToOne(fetch = FetchType.EAGER)
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
 
        public Builder introduced(LocalDate introduced) 
        {
            this.computer.introduced = introduced;
            return this;
        }
 
        public Builder discontinued(LocalDate discontinued) 
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

	public LocalDate getIntroduced() 
	{
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) 
	{
		this.introduced = introduced;
	}

	public LocalDate getDiscontinued() 
	{
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) 
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

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result
				+ ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((introduced == null) ? 0 : introduced.hashCode());
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
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (id != other.id)
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
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
		return "Computer [id=" + id + ", name=" + name + ", introduced="
				+ introduced + ", discontinued=" + discontinued + ", company="
				+ company + "]";
	}
}
