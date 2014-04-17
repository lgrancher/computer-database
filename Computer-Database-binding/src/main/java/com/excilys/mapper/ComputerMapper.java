package com.excilys.mapper;

import java.util.ArrayList;
import java.util.List;

import com.excilys.DTO.ComputerDTO;
import com.excilys.om.Company;
import com.excilys.om.Computer;

public class ComputerMapper 
{
	public static Computer dtoToComputer(ComputerDTO computerDTO)
	{		
		String idComputer = computerDTO.getId();
		long id;
		
		try
		{
			id = Long.parseLong(idComputer);
		}
		
		catch(NumberFormatException e)
		{
			id = 0;
		}
		
		String name = computerDTO.getName();
		
		Company company = CompanyMapper.dtoTOCompany(computerDTO.getCompanyDTO());
		
		Computer computer = Computer.builder()
									.id(id)
									.name(name)
									.introduced(DateMapper.stringToLocalDate(computerDTO.getIntroduced()))
									.discontinued(DateMapper.stringToLocalDate(computerDTO.getDiscontinued()))
									.company(company)
									.build();		
		return computer;
	}
	
	public static ComputerDTO computerToDTO(Computer computer)
	{		
		ComputerDTO computerDTO = ComputerDTO.builder()
											 .id(computer.getId()+"")
											 .name(computer.getName())
											 .introduced(DateMapper.localDateToString(computer.getIntroduced()))
											 .discontinued(DateMapper.localDateToString(computer.getDiscontinued()))
											 .companyDTO(CompanyMapper.companyToDTO(computer.getCompany()))
											 .build();
		
		return computerDTO;
	}
	
	public static List<ComputerDTO> computerToDTO(List<Computer> computers)
	{
		List<ComputerDTO> listComputersDTO = new ArrayList<ComputerDTO>();
		
		for(Computer computer : computers)
		{
			listComputersDTO.add(computerToDTO(computer));
		}
		
		return listComputersDTO;
	}
}
