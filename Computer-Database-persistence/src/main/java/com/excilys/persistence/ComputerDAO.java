package com.excilys.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.excilys.DTO.ComputerDTO;
import com.excilys.mapper.ComputerMapper;
import com.excilys.om.Computer;
import com.excilys.om.Page;

@Repository
public class ComputerDAO extends JdbcDaoSupport
{		
	@PersistenceContext
	protected EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<ComputerDTO> retrieve(Page<?> page) 
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
		StringBuilder sql = new StringBuilder ("SELECT computer FROM Computer computer LEFT JOIN computer.company company WHERE computer.name LIKE :search OR company.name LIKE :search ORDER BY ");
		sql.append(page.getSort());
		
		Query q = entityManager.createQuery(sql.toString());
		q.setParameter("search", search);
		q.setFirstResult(page.getOffset());
		q.setMaxResults(Page.getRecordsPerPages());

		List<ComputerDTO> listComputerDTO = ComputerMapper.computerToDTO((List<Computer>)q.getResultList());
		
		page.setListeElements(listComputerDTO);
		
		return listComputerDTO;
	}
	
	public Long create(ComputerDTO computerDTO) 
	{
		Computer computer = ComputerMapper.dtoToComputer(computerDTO);
			
		entityManager.persist(computer);
			
		return computer.getId();
	}
	
	public ComputerDTO find(Long id) 
	{			
		String sql = "SELECT computer FROM Computer computer LEFT JOIN computer.company company WHERE computer.id=:idComputer";
		ComputerDTO computerDTO;
		
		try
		{
			Query q = entityManager.createQuery(sql.toString());
			q.setParameter("idComputer", id);
			
			computerDTO = ComputerMapper.computerToDTO((Computer) q.getSingleResult());
		}
		
		catch(NoResultException e)
		{
			computerDTO = null;
	    }
		
		return computerDTO;
	}
	
	public void update(ComputerDTO computerDTO) 
	{
		Computer computer = ComputerMapper.dtoToComputer(computerDTO);
		
		entityManager.merge(computer);
	}
	
	public void delete(ComputerDTO computerDTO) 
	{		
		Computer computer = ComputerMapper.dtoToComputer(computerDTO);
		
		computer = entityManager.merge(computer);
		entityManager.remove(computer);
	}

	public int size(String search) 
	{		
		String sql = "SELECT count(computer.id) from Computer computer LEFT JOIN computer.company company WHERE computer.name LIKE :search OR company.name LIKE :search";		
		String realSearch = "%";
		
		// nombre de computers qui correspondent a la recherche
		if(search != null && !search.equals(""))
		{
			StringBuilder builder = new StringBuilder("%");
			builder.append(search);
			builder.append("%");
			
			realSearch = builder.toString();
		}
		
		Query q = entityManager.createQuery(sql.toString());
		q.setParameter("search", realSearch);

		int nbLignes = Integer.parseInt(q.getSingleResult()+"");

		return nbLignes;		
	}
	
	public long lastId() 
	{
		String sql = "select auto_increment from information_schema.tables where table_name=\"computer\"";

		int lastId = getJdbcTemplate().queryForObject(sql, Integer.class)-1;

		return lastId;
	}
}
