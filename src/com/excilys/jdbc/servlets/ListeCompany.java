package com.excilys.jdbc.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.jdbc.classes.CompanyDAO;
import com.excilys.jdbc.classes.ComputerDAO;
import com.excilys.jdbc.classes.ConnectionJDBC;
import com.excilys.jdbc.classes.Requetes;

/**
 * Servlet implementation class Insertion
 */
@WebServlet("/Insertion")
public class ListeCompany extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListeCompany() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try 
		{
			Connection connection = ConnectionJDBC.connection();
			Statement st = connection.createStatement();
			
			Requetes requetes = new Requetes(st);
			ArrayList<CompanyDAO> listeCompany = requetes.getListCompany();
	
			request.setAttribute("listeCompany", listeCompany);
			ConnectionJDBC.fermerConnection(connection);
		} 
		
		catch (ClassNotFoundException | SQLException e)
		{
			request.setAttribute("reponse", "Problème de connexion");
			e.printStackTrace();
		} 
		
		finally
		{
			request.getRequestDispatcher("addComputer.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
