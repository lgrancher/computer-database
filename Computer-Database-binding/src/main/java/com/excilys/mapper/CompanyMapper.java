package com.excilys.mapper;

import com.excilys.DTO.CompanyDTO;
import com.excilys.om.Company;

public class CompanyMapper
{	
	public static Company dtoTOCompany(CompanyDTO companyDTO)
	{
		Long id = Long.parseLong(companyDTO.getId());
		
		if(id==0)
		{
			id = null;
		}
		
		Company company = Company.builder()
									.id(id)
									.name(companyDTO.getName())
									.build();
		return company;
	}
	
	public static CompanyDTO companyToDTO(Company company)
	{
		CompanyDTO companyDTO = CompanyDTO.builder()
											 .id(company.getId()+"")
											 .name(company.getName())
											 .build();
		
		return companyDTO;
	}
}
