package com.excilys.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.excilys.om.Computer;
import com.excilys.om.Page;
import com.excilys.om.QCompany;
import com.excilys.om.QComputer;
import com.mysema.query.jpa.impl.JPAQuery;

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
		
		JPAQuery query = new JPAQuery(entityManager);
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;
		
		query.from(computer)
		   	 .leftJoin(computer.company, company)
		     .where(computer.name.like(search).or(company.name.like(search)))
		     .offset(page.getOffset())
		     .limit(Page.getRecordsPerPages());
	
		if(page.getSort().equals("id"))
		{
			query.orderBy(computer.id.asc());
		}
		
		else if(page.getSort().equals("name"))
		{
			query.orderBy(computer.name.asc());
		}
		
		else if(page.getSort().equals("company"))
		{	
			query.orderBy(company.name.asc(), computer.name.asc());
		}
		
		else if (page.getSort().equals("introduced"))
		{
			query.orderBy(computer.introduced.asc(), computer.name.asc());
		}
		
		else if (page.getSort().equals("discontinued"))
		{
			query.orderBy(computer.discontinued.asc(), computer.name.asc());
		}
		
		return query.list(computer);
	}
	
	public Long create(Computer computer) 
	{
		entityManager.persist(computer);
			
		return computer.getId();
	}
	
	public Computer find(Long id) 
	{				
		JPAQuery query = new JPAQuery(entityManager);
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;
		
		Computer computerFound = query.from(computer)
									  .leftJoin(computer.company, company)
									  .where(computer.id.eq(id))
									  .uniqueResult(computer);
		
		return computerFound;
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
		StringBuilder builder = new StringBuilder("%");
		
		// nombre de computers qui correspondent a la recherche
		if(search != null && !search.equals(""))
		{
			builder.append(search);
			builder.append("%");
		}
		
		String realSearch = builder.toString();
		
		JPAQuery query = new JPAQuery(entityManager);
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;
		
		int nbLignes = (int) query.from(computer)
								   .leftJoin(computer.company, company)
								   .where(computer.name.like(realSearch).or(company.name.like(realSearch)))
								   .count();

		return nbLignes;		
	}
	
	public long lastId() 
	{
		String sql = "select auto_increment from information_schema.tables where table_name=\"computer\"";

		int lastId = getJdbcTemplate().queryForObject(sql, Integer.class)-1;

		return lastId;
	}
}
