package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.DTO.ComputerDTO;
import com.excilys.om.Company;
import com.excilys.om.Computer;

public class ComputerRowMapper implements RowMapper<ComputerDTO>
{
	@Override
	public ComputerDTO mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		Company company = Company.builder()
								 .id(rs.getLong("computer.company_id"))
								 .name(rs.getString("company.name"))
								 .build();
		
		Computer computer = Computer.builder()
									.id(rs.getLong("computer.id"))
									.name(rs.getString("computer.name"))
									.introduced(DateMapper.sqlToLocalDate(rs.getDate("computer.introduced")))
									.discontinued(DateMapper.sqlToLocalDate(rs.getDate("computer.discontinued")))
									.company(company)
									.build();
		
		ComputerDTO computerDTO = ComputerMapper.computerToDTO(computer);
		
		return computerDTO;
	}	
}
