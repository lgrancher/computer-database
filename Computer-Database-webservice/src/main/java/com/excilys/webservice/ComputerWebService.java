package com.excilys.webservice;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.om.Computer;
import com.excilys.service.ComputerService;
 
@WebService
@Component
public class ComputerWebService 
{ 
	@Autowired
	private ComputerService computerService;
	
	@GET
	public Response getComputers() 
	{
		List<Computer> result = computerService.findAll();
		return Response.status(200).entity(result.toString()).build();
	}
}