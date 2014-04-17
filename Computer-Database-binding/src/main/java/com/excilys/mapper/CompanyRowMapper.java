package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.DTO.CompanyDTO;
import com.excilys.om.Company;

public class CompanyRowMapper implements RowMapper<CompanyDTO>
{
	@Override
	public CompanyDTO mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		Company company = Company.builder()
				 .id(rs.getLong("id"))
				 .name(rs.getString("name"))
				 .build();
		
		CompanyDTO companyDTO = CompanyMapper.companyToDTO(company);
		
		return companyDTO;
	}
}
