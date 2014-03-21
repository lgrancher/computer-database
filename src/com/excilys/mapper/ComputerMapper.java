package com.excilys.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.excilys.DTO.ComputerDTO;
import com.excilys.om.Company;
import com.excilys.om.Computer;

public class ComputerMapper 
{
	private static ComputerMapper computerMapper;
	private ComputerDTO computerDTO;
	
	private ComputerMapper(ComputerDTO computerDTO)
	{
		this.computerDTO = computerDTO;
	}
	
	public static Computer mapping(ComputerDTO computerDTO)
	{
		computerMapper = new ComputerMapper(computerDTO);
		
		String idComputer = computerDTO.getId();
		Long id;
		
		if(idComputer!=null)
		{
			id = Long.parseLong(idComputer);
		}
		
		else
		{
			id=(long) 0;
		}
		
		String name = computerDTO.getName();
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
		Date introduced;
		Date discontinued;
		
		try 
		{
			introduced = sdf.parse(computerDTO.getIntroduced());
		} 
		
		catch (ParseException e) 
		{
			introduced = new Date(0);
		}
		
		try
		{
			discontinued = sdf.parse(computerDTO.getDiscontinued());
		}
		
		catch (ParseException e) 
		{
			discontinued = new Date(0);
		}
		
		String theCompany = computerDTO.getIdCompany();
		Long idCompany = Long.parseLong(theCompany);
		
		Company company = Company.builder()
								 .id(idCompany)
								 .build();
		
		Computer computer = Computer.builder()
									.id(id)
									.name(name)
									.introduced(introduced)
									.discontinued(discontinued)
									.company(company)
									.build();		
		return computer;
	}
}
