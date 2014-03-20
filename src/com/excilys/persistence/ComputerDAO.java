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
import com.excilys.om.ComputerWrapper;

public class ComputerDAO
{
	private static ComputerDAO computerDAO;
	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	private static ConnectionJDBC connectionJDBC;
	
	private ComputerDAO()
	{
		connectionJDBC = ConnectionJDBC.getInstance();
	}
	
	public static ComputerDAO getInstance() throws SQLException
	{
		if(computerDAO==null)
		{
			computerDAO = new ComputerDAO();
			logger.info("Initialisation de computerDAO");
		}
		
		return computerDAO;
	}
	
	public List<Computer> retrieve(ComputerWrapper computerWrapper) throws SQLException
	{				
		Connection connection = connectionJDBC.getConnection();
		switch(computerWrapper.getSort())
		{
			case "name" :
				computerWrapper.setSort("computer.name");
				break;
				
			case "introduced" :
				computerWrapper.setSort("computer.introduced");
				break;
			
			case "discontinued" :
				computerWrapper.setSort("computer.discontinued");
				break;
				
			case "company" :
				computerWrapper.setSort("company.name, computer.name");
				break;
				
			default :
				computerWrapper.setSort("computer.id");	
				break;
		}
		
		String search;
		
		// on affiche tous les computers		
		if(computerWrapper.getSearch().equals("%"))
		{
			logger.info("Listing de tous les computers page "+computerWrapper.getCurrentPage() + "/" + computerWrapper.getNoOfPages());
			search = "%";
		}
		
		// filter by name
		else
		{
			StringBuilder builder = new StringBuilder("%");
			builder.append(computerWrapper.getSearch());
			builder.append("%");
			search = builder.toString();
			
			logger.info("Listing des computers avec la recherche "+computerWrapper.getSearch()+" page "+computerWrapper.getCurrentPage() + "/" + computerWrapper.getNoOfPages());
		}
		
		// requete
		StringBuilder sql = new StringBuilder ("select computer.id, computer.name, computer.introduced, computer.discontinued, computer.id, company.name from computer left join company on computer.company_id = company.id where computer.name like ? or company.name like ? order by ");
		sql.append(computerWrapper.getSort());
		sql.append(" limit ?, ?");
		
		ArrayList<Computer> listeComputers = new ArrayList<Computer>();
		
		PreparedStatement st = connection.prepareStatement(sql.toString());
		
		st.setString(1,search);
		st.setString(2,search);
		st.setInt(3, computerWrapper.getOffset());
		st.setInt(4, computerWrapper.getRecordsPerPage());
		
		ResultSet rs = st.executeQuery();
		
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

		ConnectionJDBC.close(rs,st);
		
		return listeComputers;
	}
	
	public int create(Computer computer) throws SQLException
	{
		String sql = "insert into computer values(default,?,?,?,?)";
		
		Connection connection = connectionJDBC.getConnection();
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
		
		st.executeUpdate();
		
		ResultSet rs = st.getGeneratedKeys();
	
		int idAdd=0;
		
		if( rs.next() ) 
		{
			idAdd = rs.getInt(1);
		}
		
		logger.info("Création d'un computer "+computer.getName() + ", id = "+idAdd);
		ConnectionJDBC.close(rs,st);
		
		return idAdd;
	}
	
	public Computer find(Long id) throws SQLException 
	{		
		String sql = "select * from computer where id=?";
		
		Connection connection = connectionJDBC.getConnection();
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
		
		ConnectionJDBC.close(rs,st);
		
		return computer;
	}
	
	public void update(Computer computer) throws SQLException
	{
		String sql = "update computer set name=?, introduced=?, discontinued=?, company_id=? where id=?";
		
		Connection connection = connectionJDBC.getConnection();
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
		
		logger.info("Mise à jour du computer "+computer.getId());
		st.setLong(5, computer.getId());
		
		st.executeUpdate();
 
		ConnectionJDBC.close(null,st);
	}
	
	public void delete(Computer computer) throws SQLException 
	{
		String sql = "delete from computer where id=?";
		PreparedStatement st=null;
		
		Connection connection = connectionJDBC.getConnection();
		st = connection.prepareStatement(sql);
		st.setLong(1, computer.getId());
	
		logger.info("Suppression du computer n°"+computer.getId());
		st.executeUpdate();
		
		ConnectionJDBC.close(null,st);
	}

	public int size(String name) throws SQLException
	{
		String sql = "select count(*) from computer left join company on computer.company_id = company.id where computer.name like ? or company.name like ?";

		Connection connection = connectionJDBC.getConnection();
		PreparedStatement st = connection.prepareStatement(sql);
		
		// nombre de computers en tout
		if(name.equals("%") || name.equals(""))
		{
			st.setString(1,"%");
			st.setString(2,"%");
			logger.info("Recherche du nombre de computers en tout");
		}
		
		// nombre de computers qui correspondent a la recherche
		else
		{
			StringBuilder search = new StringBuilder("%");
			search.append(name);
			search.append("%");
			
			st.setString(1,search.toString());	
			st.setString(2,search.toString());
			logger.info("Recherche du nombre de computer correspondant à "+name);
		}
	
		ResultSet rs = st.executeQuery();
		rs.next();	
		int nbLignes = rs.getInt(1);
		
		ConnectionJDBC.close(rs,st);
		
		return nbLignes;		
	}
}
