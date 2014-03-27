package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.DTO.CompanyDTO;
import com.excilys.mapper.CompanyMapper;
import com.excilys.om.Company;

public enum CompanyDAO 
{	
	INSTANCE;
	public List<CompanyDTO> retrieveAll() throws SQLException
	{		
		String sql = "select * from company";
		
		Connection connection = ConnectionJDBC.INSTANCE.getConnection();
		
		PreparedStatement st=null;
		ResultSet rs=null;
		ArrayList<CompanyDTO> listeCompanyDTO = new ArrayList<CompanyDTO>();
		
		st = connection.prepareStatement(sql);
		rs = st.executeQuery();
		
		while(rs.next())
		{
			Company company = Company.builder()
				    .id(rs.getLong(1))
					.name(rs.getString(2))
					.build();
			
			listeCompanyDTO.add(CompanyMapper.companyToDTO(company));
		}
		
		ConnectionJDBC.INSTANCE.close(rs,st);
		
		return listeCompanyDTO;
	}
}
