package com.excilys.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.*;

import com.excilys.om.Company;
import com.excilys.om.Computer;

public class ComputerDAO
{
	private static ComputerDAO computerDAO;
	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	
	private ComputerDAO(){}
	
	public static ComputerDAO getInstance()
	{
		if(computerDAO==null)
		{
			computerDAO = new ComputerDAO();
			logger.info("Initialisation de computerDAO");
		}
		
		return computerDAO;
	}
	
	public List<Computer> retrieveAll(String sort, int offset, int noOfRecords) throws SQLException
	{				
		switch(sort)
		{
			case "name" :
				sort="computer.name";
				break;
				
			case "introduced" :
				sort="computer.introduced";
				break;
			
			case "discontinued" :
				sort="computer.discontinued";
				break;
				
			case "company" :
				sort = "company.name";
				break;
				
			default :
				sort = "computer.id";	
				break;
		}
		
		Connection connection = ConnectionJDBC.getInstance();
		StringBuilder sql = new StringBuilder ("select computer.id, computer.name, computer.introduced, computer.discontinued, computer.id, company.name from computer left join company on computer.company_id = company.id order by ");
		sql.append(sort);
		sql.append(" limit ?, ?");
		
		PreparedStatement st = connection.prepareStatement(sql.toString());
		st.setInt(1, offset);
		st.setInt(2, noOfRecords);
		
		ResultSet rs = st.executeQuery();
		ArrayList<Computer> listeComputers = new ArrayList<Computer>();
		
		logger.info("Listing computers");
		while(rs.next())
		{
			Company company = new Company();
			company.setId(rs.getLong(5));
			company.setName(rs.getString(6));	
			
			Computer computer = Computer.builder()
					.id(rs.getLong(1))
	                .name(rs.getString(2))
	                .introduced(rs.getDate(3))
	                .discontinued(rs.getDate(4))
	                .company(company)
	                .build();
			
			listeComputers.add(computer); 
		}

		ConnectionJDBC.close(connection);
		
		return listeComputers;
	}
	
	public void create(Computer computer) throws SQLException
	{
		Connection connection = ConnectionJDBC.getInstance();
		String sql = "insert into computer values(default,?,?,?,?)";
		PreparedStatement st = connection.prepareStatement(sql);
 
		java.sql.Date introducedDate = new java.sql.Date(computer.getIntroduced().getTime());
		java.sql.Date discontinuedDate = new java.sql.Date(computer.getDiscontinued().getTime());

		st.setString(1,computer.getName());
		st.setDate(2, introducedDate);
		st.setDate(3,(Date) discontinuedDate);
		
		if(computer.getCompany().getId()>0)
		{
			st.setLong(4, computer.getCompany().getId());
		}
		
		else
		{
			st.setNull(4, Types.BIGINT);
		}
		
		logger.info("Création d'un computer "+computer.getName());
		st.executeUpdate();	
		
		ConnectionJDBC.close(connection);
	}
	
	public List<Computer> find(String sort, String name, int offset, int noOfRecords) throws SQLException
	{		
		switch(sort)
		{
			case "name" :
				sort="computer.name";
				break;
				
			case "introduced" :
				sort="computer.introduced";
				break;
			
			case "discontinued" :
				sort="computer.discontinued";
				break;
				
			case "company" :
				sort = "company.name";
				break;
				
			default :
				sort = "computer.id";	
				break;
		}
				
		Connection connection = ConnectionJDBC.getInstance();
		StringBuilder sql = new StringBuilder("select computer.id, computer.name, computer.introduced, computer.discontinued, computer.id, company.name from computer left join company on computer.company_id = company.id where computer.name like ? or company.name like ? order by ");
		sql.append(sort);
		sql.append(" limit ?, ?");
		
		PreparedStatement st = connection.prepareStatement(sql.toString());
		
		StringBuilder search = new StringBuilder("%");
		search.append(name);
		search.append("%");
		
		st.setString(1,search.toString());
		st.setString(2,search.toString());
		st.setInt(3, offset);
		st.setInt(4, noOfRecords);
		
		ResultSet rs = st.executeQuery();
		ArrayList<Computer> listeComputers = new ArrayList<Computer>();
		
		logger.info("Search by name "+name);
		
		while(rs.next())
		{
			Company company = new Company();
			company.setId(rs.getLong(5));
			company.setName(rs.getString(6));
			
			Computer computer = Computer.builder()
					.id(rs.getLong(1))
	                .name(rs.getString(2))
	                .introduced(rs.getDate(3))
	                .discontinued(rs.getDate(4))
	                .company(company)
	                .build();
			
			listeComputers.add(computer); 
		}

		ConnectionJDBC.close(connection);
		
		return listeComputers;
	}
	
	public Computer find(Long id) throws SQLException
	{		
		Connection connection = ConnectionJDBC.getInstance();
		String sql = "select * from computer where id=?";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setLong(1, id);
		
		logger.info("Recherche computer n° "+id);
		ResultSet rs = st.executeQuery();
		rs.next();
		
		Company company = new Company();
		company.setId(rs.getLong(5));	
		
		Computer computer = Computer.builder()
				.id(rs.getLong(1))
                .name(rs.getString(2))
                .introduced(rs.getDate(3))
                .discontinued(rs.getDate(4))
                .company(company)
                .build();
		
		ConnectionJDBC.close(connection);
		
		return computer;
	}
	
	public void update(Computer computer) throws SQLException
	{
		Connection connection = ConnectionJDBC.getInstance();
		String sql = "update computer set name=?, introduced=?, discontinued=?, company_id=? where id=?";
		PreparedStatement st = connection.prepareStatement(sql);
		
		java.sql.Date introducedDate = new java.sql.Date(computer.getIntroduced().getTime());
		java.sql.Date discontinuedDate = new java.sql.Date(computer.getDiscontinued().getTime());

		st.setString(1, computer.getName());
		st.setDate(2, introducedDate);
		st.setDate(3, discontinuedDate);
		
		if(computer.getCompany().getId()>0)
		{
			st.setLong(4, computer.getCompany().getId());
		}
		
		else
		{
			st.setNull(4, Types.BIGINT);
		}
		
		logger.info("Update computer "+computer.getId());
		st.setLong(5, computer.getId());
		
		st.executeUpdate();
		
		ConnectionJDBC.close(connection);
	}
	
	public void delete(Computer computer) throws SQLException
	{
		Connection connection = ConnectionJDBC.getInstance();
		String sql = "delete from computer where id=?";
		PreparedStatement st = connection.prepareStatement(sql);
		
		st.setLong(1, computer.getId());
		
		logger.info("Delete computer n°"+computer.getId());
		st.executeUpdate();
		
		ConnectionJDBC.close(connection);		
	}
	
	public int sizeAll() throws SQLException
	{
		Connection connection = ConnectionJDBC.getInstance();
		String sql = "select count(*) from computer";
		PreparedStatement st = connection.prepareStatement(sql);
		
		logger.info("Recherche du nombre de computers");
		ResultSet rs = st.executeQuery();
		rs.next();	
		int nbLignes = rs.getInt(1);
		
		ConnectionJDBC.close(connection);
		
		return nbLignes;		
	}
	
	public int size(String name) throws SQLException
	{
		Connection connection = ConnectionJDBC.getInstance();
		String sql = "select count(*) from computer left join company on computer.company_id = company.id where computer.name like ? or company.name like ?";
		PreparedStatement st = connection.prepareStatement(sql);
		
		StringBuilder search = new StringBuilder("%");
		search.append(name);
		search.append("%");
		
		st.setString(1,search.toString());
		st.setString(2,search.toString());
		
		logger.info("Recherche du nombre de computer correspondant à "+name);
		ResultSet rs = st.executeQuery();
		rs.next();	
		int nbLignes = rs.getInt(1);
		
		ConnectionJDBC.close(connection);
		
		return nbLignes;		
	}
}
