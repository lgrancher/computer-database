package com.excilys.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.excilys.om.Log;

@Repository
public class LogDAO
{		
	@PersistenceContext
	protected EntityManager entityManager;
	
	public void create(Log log) 
	{
		entityManager.persist(log);
	}
}
