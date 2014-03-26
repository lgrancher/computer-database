package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.*;

import com.excilys.DTO.CompanyDTO;
import com.excilys.mapper.CompanyMapper;
import com.excilys.om.Company;

public class CompanyDAO 
{
	private static CompanyDAO companyDAO;
	private static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	private static ConnectionJDBC connectionJDBC;
	
	private CompanyDAO()
	{
		connectionJDBC = ConnectionJDBC.getInstance();
	}
	
	public static CompanyDAO getInstance()
	{
		if(companyDAO==null)
		{
			logger.info("Initialisation de companyDAO");
			companyDAO = new CompanyDAO();
		}
		
		return companyDAO;
	}
	
	public List<CompanyDTO> retrieveAll() throws SQLException
	{		
		String sql = "select * from company";
		
		Connection connection = connectionJDBC.getConnection();
		
		PreparedStatement st=null;
		ResultSet rs=null;
		ArrayList<CompanyDTO> listeCompanyDTO = new ArrayList<CompanyDTO>();
		
		st = connection.prepareStatement(sql);
		rs = st.executeQuery();
		
		logger.info("Listing company");
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
}
