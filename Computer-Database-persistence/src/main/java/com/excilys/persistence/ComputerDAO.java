package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.excilys.DTO.ComputerDTO;
import com.excilys.mapper.ComputerMapper;
import com.excilys.mapper.ComputerRowMapper;
import com.excilys.mapper.DateMapper;
import com.excilys.om.Computer;
import com.excilys.om.Page;

@Repository
public class ComputerDAO extends JdbcDaoSupport
{		
	public List<ComputerDTO> retrieve(Page<?> page) throws SQLException
	{				
		String search;
	
		// on affiche tous les computers		
		if(page.getSearch().equals(""))
		{
			search = "%";
		}
		
		// filter by name
		else
		{
			StringBuilder builder = new StringBuilder("%");
			builder.append(page.getSearch());
			builder.append("%");
			search = builder.toString();
		}
		
		// requete
		StringBuilder sql = new StringBuilder ("select computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name from computer left join company on computer.company_id = company.id where computer.name like ? or company.name like ? order by ");
		sql.append(page.getSort());
		sql.append(" limit ?, ?");

		List<ComputerDTO> listComputersDTO = getJdbcTemplate().query(sql.toString(),
																  new Object[] {search, search, page.getOffset(), Page.getRecordsPerPages()},
																  new ComputerRowMapper());
		
		page.setListeElements(listComputersDTO);
		
		return listComputersDTO;
	}
	
	public Long create(ComputerDTO computerDTO) throws SQLException
	{
		final String sql = "insert into computer values(default,?,?,?,?)";
		
		final Computer computer = ComputerMapper.dtoToComputer(computerDTO);
			
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() 
		{
			  public PreparedStatement createPreparedStatement(Connection connection) throws SQLException 
			  {
			    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			    ps.setString(1, computer.getName());
			    ps.setDate(2, DateMapper.localDateToSql(computer.getIntroduced()));
			    ps.setDate(3, DateMapper.localDateToSql(computer.getDiscontinued()));
			    
			    if(computer.getCompany().getId()!=null)
			    {
			    	ps.setLong(4, computer.getCompany().getId());
			    }
			    
			    else
			    {
			    	ps.setNull(4, Types.BIGINT);
			    }
			    
			    return ps;
			  }
			
		}, keyHolder);
			
		return new Long(keyHolder.getKey().longValue());
	}
	
	public ComputerDTO find(Long id) throws SQLException 
	{			
		String sql = "select computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name  from computer left join company on computer.company_id=company.id where computer.id=?";
		
		ComputerDTO computerDTO = (ComputerDTO) getJdbcTemplate().queryForObject(sql, new Object[] {id}, new ComputerRowMapper());
		
		return computerDTO;
	}
	
	public void update(ComputerDTO computerDTO) throws SQLException
	{
		String sql = "update computer set name=?, introduced=?, discontinued=?, company_id=? where id=?";
		
		Computer computer = ComputerMapper.dtoToComputer(computerDTO);
		
		getJdbcTemplate().update(sql, new Object[] {computer.getName(),
													DateMapper.localDateToSql(computer.getIntroduced()),
													DateMapper.localDateToSql(computer.getDiscontinued()), 
													computer.getCompany().getId(), 
													computer.getId()});
	}
	
	public void delete(ComputerDTO computerDTO) throws SQLException 
	{		
		String sql = "delete from computer where id=?";
		
		getJdbcTemplate().update(sql, new Object[] {computerDTO.getId()});
	}

	public int size(String search) throws SQLException
	{		
		String sql = "select count(*) from computer left join company on computer.company_id = company.id where computer.name like ? or company.name like ?";		
		String realSearch = "%";
		
		// nombre de computers qui correspondent a la recherche
		if(search != null && !search.equals(""))
		{
			StringBuilder builder = new StringBuilder("%");
			builder.append(search);
			builder.append("%");
			
			realSearch = builder.toString();
		}

		int nbLignes = getJdbcTemplate().queryForObject(sql, Integer.class, realSearch, realSearch);

		return nbLignes;		
	}
	
	public long lastId() throws SQLException
	{
		String sql = "select auto_increment from information_schema.tables where table_name=\"computer\"";

		int lastId = getJdbcTemplate().queryForObject(sql, Integer.class)-1;

		return lastId;
	}
}
