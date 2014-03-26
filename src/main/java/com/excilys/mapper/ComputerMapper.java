package com.excilys.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.excilys.DTO.ComputerDTO;
import com.excilys.om.Company;
import com.excilys.om.Computer;

public class ComputerMapper 
{
	public static Computer dtoToComputer(ComputerDTO computerDTO)
	{		
		String idComputer = computerDTO.getId();
		Long id;
		
		try
		{
			id = Long.parseLong(idComputer);
		}
		
		catch(NumberFormatException e)
		{
			id=(long) 0;
		}
		
		String name = computerDTO.getName();
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
		
		Date introduced = new Date(0);
		Date discontinued = new Date(0);
		
		try 
		{
			introduced = sdf.parse(computerDTO.getIntroduced());
		} 
		
		catch (ParseException e){}
		
		try
		{
			discontinued = sdf.parse(computerDTO.getDiscontinued());
		}
		
		catch (ParseException e){}
		
		Company company = CompanyMapper.dtoTOCompany(computerDTO.getCompanyDTO());
		
		Computer computer = Computer.builder()
									.id(id)
									.name(name)
									.introduced(introduced)
									.discontinued(discontinued)
									.company(company)
									.build();		
		return computer;
	}
	
	public static ComputerDTO computerToDTO(Computer computer)
	{
		Date introducedDate = computer.getIntroduced();
		String introduced = "";
		
		if(introducedDate!=null)
		{
			introduced = introducedDate+"";
		}
		
		Date discontinuedDate = computer.getDiscontinued();
		String discontinued = "";
		
		if(discontinuedDate!=null)
		{
			discontinued = discontinuedDate+"";
		}
		
		ComputerDTO computerDTO = ComputerDTO.builder()
											 .id(computer.getId()+"")
											 .name(computer.getName())
											 .introduced(introduced)
											 .discontinued(discontinued)
											 .companyDTO(CompanyMapper.companyToDTO(computer.getCompany()))
											 .build();
		
		return computerDTO;
	}
}
