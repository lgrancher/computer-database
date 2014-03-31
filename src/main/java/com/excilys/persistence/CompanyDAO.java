package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.DTO.CompanyDTO;
import com.excilys.mapper.CompanyMapper;
import com.excilys.om.Company;

@Repository
public class CompanyDAO 
{	
	@Autowired
	private ConnectionJDBC connectionJDBC;
	
	public List<CompanyDTO> retrieveAll() throws SQLException
	{		
		String sql = "select * from company";
		
		Connection connection = connectionJDBC.getConnection();
		
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
		
		ConnectionJDBC.close(rs,st);
		
		return listeCompanyDTO;
	}

	public ConnectionJDBC getConnectionJDBC() 
	{
		return connectionJDBC;
	}

	public void setConnectionJDBC(ConnectionJDBC connectionJDBC) 
	{
		this.connectionJDBC = connectionJDBC;
	}
}
