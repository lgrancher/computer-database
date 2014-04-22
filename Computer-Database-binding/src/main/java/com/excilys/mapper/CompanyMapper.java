package com.excilys.mapper;

import java.util.ArrayList;
import java.util.List;

import com.excilys.DTO.CompanyDTO;
import com.excilys.om.Company;

public class CompanyMapper
{	
	public static Company dtoTOCompany(CompanyDTO companyDTO)
	{
		Company company = null;
		Long id = Long.parseLong(companyDTO.getId());
		
		if(id!=0)
		{
			company = Company.builder()
									.id(id)
									.name(companyDTO.getName())
									.build();
		}
		
		return company;
	}
	
	public static CompanyDTO companyToDTO(Company company)
	{
		CompanyDTO companyDTO = null;
		
		if(company!=null)
		{
			companyDTO = CompanyDTO.builder()
								   .id(company.getId()+"")
								   .name(company.getName())
								   .build();
		}
		
		return companyDTO;
	}
	
	public static List<CompanyDTO> companyToDTO(List<Company> companies)
	{
		List<CompanyDTO> listCompaniesDTO = new ArrayList<CompanyDTO>();
		
		for(Company company : companies)
		{
			listCompaniesDTO.add(companyToDTO(company));
		}
		
		return listCompaniesDTO;
	}
}
