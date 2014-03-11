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

import com.excilys.jdbc.classes.ComputerDAO;
import com.excilys.jdbc.classes.Requetes;
import com.excilys.jdbc.classes.ConnectionJDBC;

public class ListeComputer extends HttpServlet 
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		try 
		{
			Connection connection;
			connection = ConnectionJDBC.connection();
			Statement st = connection.createStatement();
			
			Requetes computer = new Requetes(st);
			ArrayList<ComputerDAO> listeComputer = computer.getListComputers();
			int nbComputer = computer.nombreComputer();
			
			request.setAttribute("listeComputer", listeComputer);
			request.setAttribute("nombreComputer", nbComputer);
			
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
