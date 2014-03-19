package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.*;

import com.excilys.om.Company;

public class CompanyDAO 
{
	private static CompanyDAO companyDAO;
	private static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	
	private CompanyDAO(){}
	
	public static CompanyDAO getInstance()
	{
		if(companyDAO==null)
		{
			logger.info("Initialisation de companyDAO");
			companyDAO = new CompanyDAO();
		}
		
		return companyDAO;
	}
	
	public List<Company> retrieveAll(Connection connection) throws SQLException
	{		
		String sql = "select * from company";
		
		PreparedStatement st=null;
		ResultSet rs=null;
		ArrayList<Company> listeCompany = new ArrayList<Company>();
		
		st = connection.prepareStatement(sql);
		rs = st.executeQuery();
		
		logger.info("Listing company");
		while(rs.next())
		{
			Company company = Company.builder()
				    .id(rs.getLong(1))
					.name(rs.getString(2))
					.build();
			
			listeCompany.add(company);
		}
		
		ConnectionJDBC.close(rs,st);
		
		return listeCompany;
	}
}
