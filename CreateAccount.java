package com.backend;

import java.io.IOException;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/CreateAccount")
public class CreateAccount extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String Name = req.getParameter("FullName").toUpperCase();
		String AccType = req.getParameter("AccountType");
		String date = req.getParameter("date");
		String Adhaar = req.getParameter("Adhaar");
		String DOB = req.getParameter("DOB");
		String Pan = req.getParameter("Pan");
		String Mobile = req.getParameter("number");
		String Email = req.getParameter("Email").toLowerCase();
		String Gender = req.getParameter("Gender");
		String MarritalStatus = req.getParameter("MarritalStatus");
		String Nationality = req.getParameter("Nationality");
		String Address = req.getParameter("Address").toUpperCase();

		try {
			Connection con = DBConnect.getConnection();

			String sql = "INSERT INTO Bank_Account(name, account_type, opening_date, aadhaar, dob, pan, mobile, email, gender, marital_status, nationality, address) VALUES (?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement ps = con.prepareStatement(sql, new String[] { "account_no" });

			ps.setString(1, Name);
			ps.setString(2, AccType);
			ps.setString(3, date);
			ps.setString(4, Adhaar);
			ps.setString(5, DOB);
			ps.setString(6, Pan);
			ps.setString(7, Mobile);
			ps.setString(8, Email);
			ps.setString(9, Gender);
			ps.setString(10, MarritalStatus);
			ps.setString(11, Nationality);
			ps.setString(12, Address);

			int row = ps.executeUpdate();

			if(row > 0){

			    ResultSet rs = ps.getGeneratedKeys();

			    if(rs.next()){

			        int accNo = rs.getInt(1);

			        resp.setContentType("text/html");

			        resp.getWriter().println("<h2>Account Created Successfully</h2>");
			        resp.getWriter().println("<h3>Welcome Mr. " + Name + "</h3>");
			        resp.getWriter().println("<h3>Account Type : " + AccType + "</h3>");
			        resp.getWriter().println("<h3>Account Number : " + accNo + "</h3>");
			    }

			}else{

			    resp.getWriter().println("Account Not Created.");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}