package com.excilys.DTO;

import javax.persistence.Entity;

import org.springframework.context.annotation.Scope;

import com.excilys.mapper.DateMapper;
import com.excilys.service.ComputerService;

@Scope("session")
@Entity
public class ComputerDTO 
{
	private String id;
	private String name;
	private String introduced;
	private String discontinued;
	private CompanyDTO company;
	
	 public static class Builder 
	 {
        ComputerDTO computerDTO;
 
        private Builder() 
        {
            computerDTO = new ComputerDTO();
        }
 
        public Builder id(String id) 
        {
            if(id != null)
                this.computerDTO.id = id;
            return this;
        }
 
        public Builder name(String name) 
        {
            this.computerDTO.name = name;
            return this;
        }
 
        public Builder introduced(String introduced) 
        {
            this.computerDTO.introduced = introduced;
            return this;
        }
 
        public Builder discontinued(String discontinued) 
        {
            this.computerDTO.discontinued = discontinued;
            return this;
        }
 
        public Builder companyDTO(CompanyDTO companyDTO) 
        {
            this.computerDTO.company = companyDTO;
            return this;
        }
 
        public ComputerDTO build() 
        {
            return this.computerDTO;
        }
	 }

	public ComputerDTO(){}

	// retourne un computerDTO si l'id existe, null sinon
	public ComputerDTO(String id, ComputerService computerService)
	{
		try
		{	
			Long numId = Long.parseLong(id);
			ComputerDTO computerFound = computerService.find(numId);
			
			if(computerFound!=null)
			{
				this.id = computerFound.getId();
				this.name = computerFound.getName();
				this.introduced = DateMapper.formatDBVersWeb(computerFound.getIntroduced());
				this.discontinued = DateMapper.formatDBVersWeb(computerFound.getDiscontinued());
				this.company = computerFound.getCompanyDTO();
			}
			
			else
			{
				this.id = id;
				this.name = null;
				this.introduced = null;
				this.discontinued = null;
				this.company = null;
			}
		}
		
		catch(NumberFormatException e)
		{
			this.id = id;
			this.name = null;
			this.introduced = null;
			this.discontinued = null;
			this.company = null;
		}
	}
	
	public String getId() 
	{
		return id;
	}

	public void setId(String id) 
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

	public String getIntroduced() 
	{
		return introduced;
	}

	public void setIntroduced(String introduced) 
	{
		this.introduced = introduced;
	}

	public String getDiscontinued() 
	{
		return discontinued;
	}

	public void setDiscontinued(String discontinued) 
	{
		this.discontinued = discontinued;
	}

	public CompanyDTO getCompanyDTO() 
	{
		return company;
	}

	public void setCompanyDTO(CompanyDTO companyDTO) 
	{
		this.company = companyDTO;
	}
	
	@Override
	public String toString() 
	{
		return "ComputerDTO [id=" + id + ", name=" + name + ", introduced="
				+ introduced + ", discontinued=" + discontinued
				+ ", companyDTO=" + company + "]";
	}

	
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result
				+ ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ComputerDTO other = (ComputerDTO) obj;
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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

	public static Builder builder() 
	{
	   return new Builder();
	}
}
