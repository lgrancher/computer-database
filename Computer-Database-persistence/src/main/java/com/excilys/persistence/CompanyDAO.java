package com.excilys.persistence;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.excilys.DTO.CompanyDTO;
import com.excilys.mapper.CompanyRowMapper;


@Repository
public class CompanyDAO extends JdbcDaoSupport
{			
	public List<CompanyDTO> retrieveAll() 
	{		
		String sql = "select * from company";
		
		List<CompanyDTO> listCompaniesDTO = getJdbcTemplate().query(sql.toString(), new CompanyRowMapper());
		
		return listCompaniesDTO;
	}
}
