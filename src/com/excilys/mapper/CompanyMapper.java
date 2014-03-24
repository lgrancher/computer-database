package com.excilys.mapper;

import com.excilys.DTO.CompanyDTO;
import com.excilys.om.Company;

public class CompanyMapper
{
	private static CompanyMapper companyMapper;
	private CompanyDTO companyDTO;
	
	public static Company dtoTOCompany(CompanyDTO companyDTO)
	{
		Company company = Company.builder()
									.id(Long.parseLong(companyDTO.getId()))
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
	
	private CompanyMapper(CompanyDTO companyDTO)
	{
		this.setCompanyDTO(companyDTO);
	}

	public static CompanyMapper getCompanyMapper()
	{
		return companyMapper;
	}

	public static void setCompanyMapper(CompanyMapper companyMapper) 
	{
		CompanyMapper.companyMapper = companyMapper;
	}

	public CompanyDTO getCompanyDTO()
	{
		return companyDTO;
	}

	public void setCompanyDTO(CompanyDTO companyDTO)
	{
		this.companyDTO = companyDTO;
	}

}
