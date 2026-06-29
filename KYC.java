package com.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/KYCServlet")
public class KYC extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String Account_No = req.getParameter("Account_No");
	String Mobile = req.getParameter("Mobile");
	String Email = req.getParameter("Email");
	String Address = req.getParameter("Address");
	
	String Sql = "Update Bank_Account Set Mobile = ?, Email = ?, Address = ? where Account_No=?";
	
	try {
		
		Connection con = DBConnect.getConnection();
		PreparedStatement pmst = con.prepareStatement(Sql);
		
		pmst.setString(1, Mobile);
		pmst.setString(2, Email);
		pmst.setString(3, Address);
		pmst.setString(4, Account_No);
		
		int i = pmst.executeUpdate();
		
		if(i>0) {
			PrintWriter out = resp.getWriter();
			out.print("Data Updated Successfully...");
			System.out.println("Data Updated Successfully...");
		}else {
			PrintWriter out = resp.getWriter();
		out.print("Error");
			System.out.println("Error plz chech details");
		}
	}catch(Exception e) {
			e.printStackTrace();
		}
	
	}
}	
	

	
