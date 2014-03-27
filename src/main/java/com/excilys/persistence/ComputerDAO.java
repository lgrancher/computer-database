package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.*;

import com.excilys.DTO.ComputerDTO;
import com.excilys.mapper.ComputerMapper;
import com.excilys.mapper.DateMapper;
import com.excilys.om.Company;
import com.excilys.om.Computer;
import com.excilys.om.Page;

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
	
	public List<ComputerDTO> retrieve(Page<?> page) throws SQLException
	{				
		Connection connection = connectionJDBC.getConnection();
	
		String search;
		
		// on affiche tous les computers		
		if(page.getSearch().equals(""))
		{
			logger.info("Listing de tous les computers page "+page.getCurrentPage() + "/" + page.getNoOfPages());
			search = "%";
		}
		
		// filter by name
		else
		{
			StringBuilder builder = new StringBuilder("%");
			builder.append(page.getSearch());
			builder.append("%");
			search = builder.toString();
			
			logger.info("Listing des computers avec la recherche "+page.getSearch()+" page "+page.getCurrentPage() + "/" + page.getNoOfPages());
		}
		
		// requete
		StringBuilder sql = new StringBuilder ("select computer.id, computer.name, computer.introduced, computer.discontinued, computer.id, company.name from computer left join company on computer.company_id = company.id where computer.name like ? or company.name like ? order by ");
		sql.append(page.getSort());
		sql.append(" limit ?, ?");

		ArrayList<ComputerDTO> listeComputersDTO = new ArrayList<ComputerDTO>();
		
		PreparedStatement st = connection.prepareStatement(sql.toString());
		
		st.setString(1,search);
		st.setString(2,search);
		st.setInt(3, page.getOffset());
		st.setInt(4, Page.getRecordsPerPages());
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			Company company = new Company();
			company.setId(rs.getLong(5));
			company.setName(rs.getString(6));	
		
			Computer computer = Computer.builder()
					.id(rs.getLong(1))
	                .name(rs.getString(2))
	                .introduced(DateMapper.sqlToLocalDate(rs.getDate(3)))
	                .discontinued(DateMapper.sqlToLocalDate(rs.getDate(4)))
	                .company(company)
	                .build();
			
			
			listeComputersDTO.add(ComputerMapper.computerToDTO(computer)); 
		}

		page.setListeElements(listeComputersDTO);
		ConnectionJDBC.close(rs,st);
		
		return listeComputersDTO;
	}
	
	public int create(ComputerDTO computerDTO) throws SQLException
	{
		Connection connection = connectionJDBC.getConnection();
		
		String sql = "insert into computer values(default,?,?,?,?)";
		
		PreparedStatement st = connection.prepareStatement(sql);
		
		Computer computer = ComputerMapper.dtoToComputer(computerDTO);
	
		st.setString(1,computer.getName());
		st.setDate(2, DateMapper.localDateToSql(computer.getIntroduced()));
		st.setDate(3, DateMapper.localDateToSql(computer.getDiscontinued()));
		
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
	
	public ComputerDTO find(Long id) throws SQLException 
	{		
		Connection connection = connectionJDBC.getConnection();
		
		String sql = "select * from computer where id=?";
		
		PreparedStatement st = connection.prepareStatement(sql);
		st.setLong(1, id);
	
		logger.info("Recherche computer n° "+id);
		ResultSet rs = st.executeQuery();
		boolean existe = rs.next();
	
		ComputerDTO computerDTO;
		
		if(existe)
		{
			Company company = new Company();
			company.setId(rs.getLong(5));	
			
			Computer computer = Computer.builder()
					.id(rs.getLong(1))
	                .name(rs.getString(2))
	                .introduced(DateMapper.sqlToLocalDate(rs.getDate(3)))
	                .discontinued(DateMapper.sqlToLocalDate(rs.getDate(4)))
	                .company(company)
	                .build();
			
			computerDTO = ComputerMapper.computerToDTO(computer);
		}
		
		else
		{
			computerDTO = null;
		}
		
		ConnectionJDBC.close(rs,st);
		
		return computerDTO;
	}
	
	public void update(ComputerDTO computerDTO) throws SQLException
	{
		Connection connection = connectionJDBC.getConnection();
		
		String sql = "update computer set name=?, introduced=?, discontinued=?, company_id=? where id=?";
		
		PreparedStatement st = connection.prepareStatement(sql);
			
		Computer computer = ComputerMapper.dtoToComputer(computerDTO);
		
		st.setString(1, computer.getName());
		st.setDate(2, DateMapper.localDateToSql(computer.getIntroduced()));
		st.setDate(3, DateMapper.localDateToSql(computer.getDiscontinued()));
		
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
	
	public void delete(ComputerDTO computerDTO) throws SQLException 
	{
		Connection connection = connectionJDBC.getConnection();
		
		String sql = "delete from computer where id=?";
		
		Computer computer = ComputerMapper.dtoToComputer(computerDTO);
		
		PreparedStatement st = connection.prepareStatement(sql);
		st.setLong(1, computer.getId());
	
		logger.info("Suppression du computer n°"+computer.getId());
		st.executeUpdate();
		
		ConnectionJDBC.close(null,st);
	}

	public int size(String search) throws SQLException
	{
		Connection connection = connectionJDBC.getConnection();
				
		String sql = "select count(*) from computer left join company on computer.company_id = company.id where computer.name like ? or company.name like ?";

		PreparedStatement st = connection.prepareStatement(sql);
		
		// nombre de computers en tout
		if(search.equals(""))
		{
			st.setString(1,"%");
			st.setString(2,"%");
			logger.info("Recherche du nombre de computers en tout");
		}
		
		// nombre de computers qui correspondent a la recherche
		else
		{
			StringBuilder builder = new StringBuilder("%");
			builder.append(search);
			builder.append("%");
			
			st.setString(1,builder.toString());	
			st.setString(2,builder.toString());
			logger.info("Recherche du nombre de computer correspondant à "+search);
		}
	
		ResultSet rs = st.executeQuery();
		rs.next();	
		int nbLignes = rs.getInt(1);
		
		ConnectionJDBC.close(rs,st);
		
		return nbLignes;		
	}
}
