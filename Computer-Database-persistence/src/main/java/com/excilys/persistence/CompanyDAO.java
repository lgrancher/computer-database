package com.excilys.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.om.Company;

public interface CompanyDAO extends JpaRepository<Company, Long> {}
