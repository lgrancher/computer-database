package com.excilys.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.*;

import com.excilys.om.Company;
import com.excilys.om.Computer;
import com.excilys.om.ComputerWrapper;

public class ComputerDAO
{
	private static ComputerDAO computerDAO;
	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	
	private ComputerDAO(){}
	
	public static ComputerDAO getInstance()
	{
		if(computerDAO==null)
		{
			computerDAO = new ComputerDAO();
			logger.info("Initialisation de computerDAO");
		}
		
		return computerDAO;
	}
	
	public List<Computer> retrieve(Connection connection, ComputerWrapper computerWrapper)
	{				
		switch(computerWrapper.getSort())
		{
			case "name" :
				computerWrapper.setSort("computer.name");
				break;
				
			case "introduced" :
				computerWrapper.setSort("computer.introduced");
				break;
			
			case "discontinued" :
				computerWrapper.setSort("computer.discontinued");
				break;
				
			case "company" :
				computerWrapper.setSort("company.name");
				break;
				
			default :
				computerWrapper.setSort("computer.id");	
				break;
		}
		
		// on affiche tous les computers		
		if(computerWrapper.getName()==null)
		{
			computerWrapper.setName("%");
		}
		
		// filter by name
		else
		{
			StringBuilder search = new StringBuilder("%");
			search.append(computerWrapper.getName());
			search.append("%");
			
			computerWrapper.setName(search.toString());
		}
		
		// requete
		StringBuilder sql = new StringBuilder ("select computer.id, computer.name, computer.introduced, computer.discontinued, computer.id, company.name from computer left join company on computer.company_id = company.id where computer.name like ? or company.name like ? order by ");
		sql.append(computerWrapper.getSort());
		sql.append(" limit ?, ?");
		
		PreparedStatement st=null;
		ResultSet rs=null;
		ArrayList<Computer> listeComputers = new ArrayList<Computer>();
		
		try
		{
			st = connection.prepareStatement(sql.toString());
			
			st.setString(1,computerWrapper.getName());
			st.setString(2,computerWrapper.getName());
			st.setInt(3, computerWrapper.getOffset());
			st.setInt(4, computerWrapper.getRecordsPerPage());
			
			rs = st.executeQuery();
			
			logger.info("Listing computers");
			
			while(rs.next())
			{
				Company company = new Company();
				company.setId(rs.getLong(5));
				company.setName(rs.getString(6));	
				
				Computer computer = Computer.builder()
						.id(rs.getLong(1))
		                .name(rs.getString(2))
		                .introduced(rs.getDate(3))
		                .discontinued(rs.getDate(4))
		                .company(company)
		                .build();
				
				listeComputers.add(computer); 
			}
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		finally
		{
			try 
			{
				rs.close();
				st.close();
			} 
			
			catch (SQLException e) 
			{
				e.printStackTrace();
			}	
		}	
		
		return listeComputers;
	}
	
	public void create(Connection connection, Computer computer)
	{
		String sql = "insert into computer values(default,?,?,?,?)";
		PreparedStatement st=null;
		
		try 
		{
			st = connection.prepareStatement(sql);
			java.sql.Date introducedDate = new java.sql.Date(computer.getIntroduced().getTime());
			java.sql.Date discontinuedDate = new java.sql.Date(computer.getDiscontinued().getTime());

			st.setString(1,computer.getName());
			st.setDate(2, introducedDate);
			st.setDate(3,(Date) discontinuedDate);
			
			if(computer.getCompany().getId()>0)
			{
				st.setLong(4, computer.getCompany().getId());
			}
			
			else
			{
				st.setNull(4, Types.BIGINT);
			}
			
			logger.info("Création d'un computer "+computer.getName());
			st.executeUpdate();	
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
 
		finally
		{
			try 
			{
				st.close();
			} 
			
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}		
	}
	
	public Computer find(Connection connection, Long id) 
	{		
		String sql = "select * from computer where id=?";
		PreparedStatement st=null;
		ResultSet rs=null;
		Computer computer=null;
		
		try 
		{
			st = connection.prepareStatement(sql);
			st.setLong(1, id);
		
			logger.info("Recherche computer n° "+id);
			rs = st.executeQuery();
			rs.next();
			
			Company company = new Company();
			company.setId(rs.getLong(5));	
			
			computer = Computer.builder()
					.id(rs.getLong(1))
	                .name(rs.getString(2))
	                .introduced(rs.getDate(3))
	                .discontinued(rs.getDate(4))
	                .company(company)
	                .build();
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		finally
		{
			try 
			{
				rs.close();
				st.close();
			} 
			
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}	
		
		return computer;
	}
	
	public void update(Connection connection, Computer computer)
	{
		String sql = "update computer set name=?, introduced=?, discontinued=?, company_id=? where id=?";
		
		PreparedStatement st=null;
		try 
		{
			st = connection.prepareStatement(sql);
			
			java.sql.Date introducedDate = new java.sql.Date(computer.getIntroduced().getTime());
			java.sql.Date discontinuedDate = new java.sql.Date(computer.getDiscontinued().getTime());

			st.setString(1, computer.getName());
			st.setDate(2, introducedDate);
			st.setDate(3, discontinuedDate);
			
			if(computer.getCompany().getId()>0)
			{
				st.setLong(4, computer.getCompany().getId());
			}
			
			else
			{
				st.setNull(4, Types.BIGINT);
			}
			
			logger.info("Update computer "+computer.getId());
			st.setLong(5, computer.getId());
			
			st.executeUpdate();
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		finally
		{
			try 
			{
				st.close();
			} 
			
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}			
	}
	
	public void delete(Connection connection, Computer computer) 
	{
		String sql = "delete from computer where id=?";
		PreparedStatement st=null;
		
		try 
		{
			st = connection.prepareStatement(sql);
			st.setLong(1, computer.getId());
		
			logger.info("Delete computer n°"+computer.getId());
			st.executeUpdate();
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		finally
		{
			try 
			{
				st.close();
			} 
			
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}	
	}

	public int size(Connection connection, String name)
	{
		String sql = "select count(*) from computer left join company on computer.company_id = company.id where computer.name like ? or company.name like ?";
		PreparedStatement st=null;
		ResultSet rs=null;
		int nbLignes=0;
		
		try 
		{
			st = connection.prepareStatement(sql);
			
			// nombre de computers en tout
			if(name==null)
			{
				st.setString(1,"%");
				st.setString(2,"%");
			}
			
			// nombre de computers qui correspondent a la recherche
			else
			{
				StringBuilder search = new StringBuilder("%");
				search.append(name);
				search.append("%");
				
				st.setString(1,search.toString());	
				st.setString(2,search.toString());
			}
		
			logger.info("Recherche du nombre de computer correspondant à "+name);
			rs = st.executeQuery();
			rs.next();	
			nbLignes = rs.getInt(1);
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		finally
		{
			try 
			{
				rs.close();
				st.close();
			} 
			
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}	
		
		return nbLignes;		
	}
}
