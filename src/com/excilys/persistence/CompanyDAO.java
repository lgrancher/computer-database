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
	
	public List<Company> retrieveAll() throws SQLException
	{		
		Connection connection = ConnectionJDBC.getInstance();
		String sql = "select * from company";
		
		PreparedStatement st = connection.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		
		ArrayList<Company> listeCompany = new ArrayList<Company>();
		
		logger.info("Listing company");
		while(rs.next())
		{
			Company company = Company.builder()
				    .id(rs.getLong(1))
					.name(rs.getString(2))
					.build();
			
			listeCompany.add(company);
		}
		
		ConnectionJDBC.close(connection);
		
		return listeCompany;
	}
	
	public Company find(Long id) throws SQLException
	{
		Connection connection = ConnectionJDBC.getInstance();
		String sql = "select * from computer where id=?";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setLong(1, id);
		
		logger.info("Recherche de la company n°"+id);
		ResultSet rs = st.executeQuery();
		rs.next();
		
		Company company = Company.builder()
			    .id(rs.getLong(1))
				.name(rs.getString(2))
				.build();
		
		ConnectionJDBC.close(connection);
		
		return company;
	}
	
}
