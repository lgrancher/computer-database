package com.excilys.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.excilys.om.Company;
import com.excilys.om.QCompany;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class CompanyDAO 
{			
	@PersistenceContext
	protected EntityManager entityManager;
	
	public List<Company> retrieveAll() 
	{	
		JPAQuery query = new JPAQuery(entityManager);
		QCompany company = QCompany.company;
		
		List<Company> listCompanies = query.from(company).list(company);
		return listCompanies;
	}
}
