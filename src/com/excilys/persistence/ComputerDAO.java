package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.om.Company;
import com.excilys.om.Computer;

public class ComputerDAO
{
	private static ComputerDAO computerDAO;
	
	private ComputerDAO()
	{
		
	}
	
	public static ComputerDAO getInstance()
	{
		if(computerDAO==null)
		{
			computerDAO = new ComputerDAO();
		}
		
		return computerDAO;
	}
	
	public List<Computer> retrieveAll() throws SQLException
	{
		Connection connection = ConnectionJDBC.getInstance();
		String sql = "select computer.id, computer.name, computer.introduced, computer.discontinued, computer.id, company.name from computer left join company on computer.company_id = company.id;";
		PreparedStatement st = connection.prepareStatement(sql);
		
		ResultSet rs = st.executeQuery();
		ArrayList<Computer> listeComputers = new ArrayList<Computer>();
		
		while(rs.next())
		{
			Computer computer = new Computer();
			Company company = new Company();
			
			computer.setId(rs.getLong(1));
			computer.setName(rs.getString(2));
			computer.setIntroduced(rs.getDate(3));
			computer.setDiscontinued(rs.getDate(4));
			company.setId(rs.getLong(5));
			company.setName(rs.getString(6));			
			computer.setCompany(company);
			
			listeComputers.add(computer); 
		}

		ConnectionJDBC.close(connection);
		
		return listeComputers;
	}
	
	public void create(Computer computer) throws SQLException
	{
		Connection connection = ConnectionJDBC.getInstance();
		String sql = "insert into computer values(\""+computer.getName()+"\",\""+computer.getIntroduced()+"\",\""+computer.getDiscontinued()+"\",\""+computer.getCompany()+"\"";
		PreparedStatement st = connection.prepareStatement(sql);
		
		st.executeUpdate();	
	}
}
