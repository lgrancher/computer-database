package com.excilys.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.excilys.om.Computer;
import com.excilys.om.Company;
import com.excilys.om.Page;

@Repository
public class ComputerDAO extends JdbcDaoSupport
{		
	@PersistenceContext
	protected EntityManager entityManager;
	
	public List<Computer> retrieve(Page<?> page) 
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
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Computer> query = builder.createQuery(Computer.class);
		Root<Computer> computer = query.from(Computer.class);		
		
		Join<Computer, Company> company = computer.join("company", JoinType.LEFT);
		
		query.where(builder.or
					(
							builder.like(computer.get("name").as(String.class),search),
							builder.like(company.get("name").as(String.class),search)
					)
				   );
		
		if(page.getSort().equals("id") || page.getSort().equals("name"))
		{
			query.orderBy(builder.asc(computer.get(page.getSort())));
		}
		
		else if(page.getSort().equals("company"))
		{	
			query.orderBy(builder.asc(company.get("name")), builder.asc(computer.get("name")));
		}
		
		else
		{
			query.orderBy(builder.asc(computer.get(page.getSort())), builder.asc(computer.get("name")));
		}
		
		
		List<Computer> listComputers = entityManager.createQuery(query)
													.setFirstResult(page.getOffset())
													.setMaxResults(Page.getRecordsPerPages())
													.getResultList();	

		
		return listComputers;
	}
	
	public Long create(Computer computer) 
	{
		entityManager.persist(computer);
			
		return computer.getId();
	}
	
	public Computer find(Long id) 
	{			
		String sql = "SELECT computer FROM Computer computer LEFT JOIN computer.company company WHERE computer.id=:idComputer";
		Computer computer;
		
		try
		{
			Query q = entityManager.createQuery(sql.toString());
			q.setParameter("idComputer", id);
			
			computer = (Computer) q.getSingleResult();
		}
		
		catch(NoResultException e)
		{
			computer = null;
	    }
		
		return computer;
	}
	
	public void update(Computer computer) 
	{
		entityManager.merge(computer);
	}
	
	public void delete(Computer computer) 
	{		
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
