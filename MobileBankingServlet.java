package com.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/MobileBankingServlet")
public class MobileBankingServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String Name = req.getParameter("Name");
	String Account = req.getParameter("Account_No");
	
	try {
		Connection con = DBConnect.getConnection();
		PreparedStatement pmst = con.prepareStatement("Select * from Bank_Account Where Name=? AND Account_No=?");
		pmst.setString(1, Name);
		pmst.setString(2, Account);
		
		ResultSet rs = pmst.executeQuery();
		
		 if (rs.next()) {
	        	PrintWriter out = resp.getWriter();
	        	resp.getWriter().println("Customer Login Successfully");
	        	resp.getWriter().print("<Well Come Mr."+Name);
	        	
	           resp.sendRedirect("MobileBankDashbord.htm");
	                   } else {
	        	PrintWriter out = resp.getWriter();

	            resp.getWriter().println("<h1>Invalid Username or Account</h1>");

	            
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}