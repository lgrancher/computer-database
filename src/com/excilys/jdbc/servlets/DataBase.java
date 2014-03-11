package com.excilys.jdbc.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.jdbc.classes.Computer;
import com.excilys.jdbc.classes.ConnectionJDBC;

public class DataBase extends HttpServlet 
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		try 
		{
			Connection connection;
			connection = ConnectionJDBC.connection();
			Statement st = connection.createStatement();
			
			Computer computer = new Computer(st);
			ArrayList<String[]> listeComputer = computer.getListComputers();
			
			request.setAttribute("reponse", listeComputer);
			ConnectionJDBC.fermerConnection(connection);	
		} 
		
		catch (ClassNotFoundException | SQLException e)
		{
			request.setAttribute("reponse", "Problème de connexion");
			e.printStackTrace();
		} 
		
		finally
		{
			request.getRequestDispatcher("Affichage.jsp").forward(request, response);
		}
	}
}
