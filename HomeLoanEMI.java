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

@WebServlet("/HomeLoanEMIServlet")
public class HomeLoanEMI extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	 
		 String emiStr = req.getParameter("GoldLoanEMI");
	        String mobile = req.getParameter("Mobile");
	        

	        PrintWriter out = resp.getWriter();

	        try {
	            int emi = Integer.parseInt(emiStr); // ✅ convert string → number

	            Connection con = DBConnect.getConnection();

	            PreparedStatement ps = con.prepareStatement(
	                "UPDATE HomeLoan SET PaidAmount = PaidAmount + ?, " +
	                "Outstanding = LoanAmount - (PaidAmount + ?) " +
	                "WHERE Mobile = ?"
	            );

	            ps.setInt(1, emi);      
	            ps.setInt(2, emi);      
	            ps.setString(3, mobile); 
	            

	            int i = ps.executeUpdate();

	            if (i > 0) {
	                out.println("EMI Paid Successfully ✅");
	            } else {
	                out.println("No record found  (Check Mobile Number)");
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            out.println("Error occurred ");
	        }
	    }
	
	}
	
	


