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

@WebServlet("/ViewBussinessLoan")
public class ViewBussinessLoan extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        try {
            Connection con = DBConnect.getConnection();

            PreparedStatement pmst = con.prepareStatement("SELECT * FROM BUSSINESSLOAN");
            ResultSet rs = pmst.executeQuery();

            out.println("<h2 style='text-align:center;'>Bussiness Loan Customers</h2>");

            out.println("<table border='1' cellspacing='0' cellpadding='10' style='width:100%; text-align:center;'>");

            out.println("<tr style='background:#f2c94c;'>"
                    + "<th>Name</th>"
                    + "<th>Mobile</th>"
                    + "<th>Experience</th>"
                    + "<th>Purpose</th>"
                    + "<th>Month Income</th>"
                    + "<th>Loan Amount</th>"
                    + "<th>Loan Type</th>"
                    + "<th>Bussi Type</th>"
                    + "<th>Owner</th>"
                    + "<th> Bussi Name</th>"
            
                     
                    + "</tr>");

            while (rs.next()) {

                out.println("<tr>"
                        + "<td>" + rs.getString("NAME") + "</td>"
                        + "<td>" + rs.getString("MOBILE") + "</td>"
                        		+ "<td>" + rs.getString("EXPERIENCE") + "</td>"
                        				+ "<td>" + rs.getString("PURPOSE") + "</td>"
                        						+ "<td>" + rs.getString("MONTHLYINCOME") + "</td>"
                        								+ "<td>" + rs.getString("LOAN AMOUNT") + "</td>"
                        										+ "<td>" + rs.getString("LOANTYPE") + "</td>"
                        												+ "<td>" + rs.getString("TYPE") + "</td>"
                        														+ "<td>" + rs.getString("OWNER") + "</td>"
                        																+ "<td>" + rs.getString("BUSSINESSNAME") + "</td>"
                        
      

                        + "</tr>");
            }

            out.println("</table>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }
}		