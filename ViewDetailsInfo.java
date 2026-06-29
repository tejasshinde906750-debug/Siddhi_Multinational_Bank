package com.backend;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/ViewDetailsServlet")
public class ViewDetailsInfo extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();

		String accno = req.getParameter("Account_No");

		try {
			Connection con = DBConnect.getConnection();

			String sql = "SELECT * FROM Bank_Account WHERE Account_No = ?";
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, accno);

			ResultSet rs = ps.executeQuery();

		
			out.println("<html><head><title>Customer Details</title>");
			out.println("<style>");
			out.println("body{font-family: Arial; background: linear-gradient(to right,#1e3c72,#2a5298); text-align:center;}");
			out.println(".card{background:white; width:400px; margin:100px auto; padding:20px; border-radius:10px; box-shadow:0px 5px 20px rgba(0,0,0,0.2);}");
			out.println("h2{color:#2a5298;}");
			out.println("table{width:100%; border-collapse:collapse;}");
			out.println("td{padding:10px; border-bottom:1px solid #ccc; text-align:left;}");
			out.println("td:first-child{font-weight:bold;}");
			out.println("</style></head><body>");

			if (rs.next()) {

				out.println("<div class='card'>");
				out.println("<h2>Customer Details</h2>");

				out.println("<table>");
				out.println("<tr><td>Name</td><td>" + rs.getString("Name") + "</td></tr>");
				out.println("<tr><td>Account No</td><td>" + rs.getString("Account_No") + "</td></tr>");
				out.println("<tr><td>Mobile</td><td>" + rs.getString("Mobile") + "</td></tr>");
				out.println("<tr><td>Email</td><td>" + rs.getString("Email").toLowerCase() + "</td></tr>");
				out.println("<tr><td>Address</td><td>" + rs.getString("Address") + "</td></tr>");
				out.println("<tr><td>Balance</td><td>" + rs.getString("Balance") + "</td></tr>");
				out.println("</table>");

				out.println("</div>");

			} else {
				out.println("<div class='card'><h2>No customer found</h2></div>");
			}

			out.println("</body></html>");

		} catch (Exception e) {
			e.printStackTrace();
			out.println("Error: " + e.getMessage());
		}
	}
}