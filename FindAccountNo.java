package com.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/FindAccNO")
public class FindAccountNo extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();

		String Name = req.getParameter("Name");

		try {
			Connection con = DBConnect.getConnection();

			PreparedStatement pmst = con.prepareStatement(
				"SELECT Account_No FROM Bank_Account WHERE Name=?");

			pmst.setString(1, Name);

			ResultSet rs = pmst.executeQuery();

			if (rs.next()) {
				String accno = rs.getString("Account_No");

				out.println("<h2>Account Number: " + accno + "</h2>");
				out.print("<h2>Customer Name  : Mr." + Name + "</h2>");

			} else {
				out.println("<h3> Record not found</h3>");
			}

		} catch (Exception e) {
			e.printStackTrace();
			out.println("Error: " + e.getMessage());
		}
	}
}