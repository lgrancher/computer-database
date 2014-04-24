package com.excilys.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;

import com.excilys.DTO.CompanyDTO;
import com.excilys.mapper.CompanyMapper;
import com.excilys.om.Company;

@Repository
public class CompanyDAO 
{			
	@PersistenceContext
	protected EntityManager entityManager;
	
	public List<CompanyDTO> retrieveAll() 
	{		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Company> q = cb.createQuery(Company.class);
		q.from(Company.class);
		List<Company> listCompanies = entityManager.createQuery(q).getResultList();
		
		return CompanyMapper.companyToDTO(listCompanies);
	}
}
