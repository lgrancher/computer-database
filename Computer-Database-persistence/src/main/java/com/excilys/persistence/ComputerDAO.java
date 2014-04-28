package com.excilys.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.excilys.om.Computer;

public interface ComputerDAO extends JpaRepository<Computer, Long>
{		
	@Query("SELECT computer FROM Computer computer LEFT JOIN computer.company company WHERE computer.name LIKE :search OR company.name LIKE :search")
	public Page<Computer> findAll(@Param("search") String search, Pageable page);
}
