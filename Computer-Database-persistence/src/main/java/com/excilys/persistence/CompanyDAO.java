package com.excilys.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.excilys.DTO.CompanyDTO;
import com.excilys.mapper.CompanyMapper;
import com.excilys.om.Company;

@Repository
public class CompanyDAO 
{			
	@PersistenceContext
	protected EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<CompanyDTO> retrieveAll() 
	{				
		List<Company> listCompanies = entityManager.createQuery("SELECT c FROM Company c").getResultList();
		
		return CompanyMapper.companyToDTO(listCompanies);
	}
}
