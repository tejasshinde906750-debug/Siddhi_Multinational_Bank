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

@WebServlet("/DeleteServlet")
public class DeleteAccount  extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String Account_no = req.getParameter("Account_No");
	
	try {
		Connection con = DBConnect.getConnection();
		PreparedStatement pmst = con.prepareStatement("delete from Bank_Account where Account_no=?");
		pmst.setString(1, Account_no);
		
		int i = pmst.executeUpdate();
		
		if(i>0) {
			System.out.println("Record Deleted Successfully...!!!");
			PrintWriter out = resp.getWriter();
			out.print("Account Deleted Successfully...");
		}else {
			
			System.out.println(" Account Number is Not Exist...!!!");
			PrintWriter out = resp.getWriter();
			out.print(" Account Number is Not Exist...!!!");
			
		}
	}catch(Exception e) {
		e.printStackTrace();
	}
	}
	
	

}
