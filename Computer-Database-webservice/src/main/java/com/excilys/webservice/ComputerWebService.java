package com.excilys.webservice;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.om.Computer;
import com.excilys.service.ComputerService;

@WebService
public class ComputerWebService 
{
	@Autowired
	private ComputerService computerService;
	
	public List<Computer> retrieve()
	{
		return computerService.findAll();
	}
}
