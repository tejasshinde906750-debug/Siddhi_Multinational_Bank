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

@WebServlet("/ViewVehicleLoanCustomer")
public class ViewVehicleLoanCustomer extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        try {
            Connection con = DBConnect.getConnection();

            PreparedStatement pmst = con.prepareStatement("SELECT * FROM VEHICLELOAN");
            ResultSet rs = pmst.executeQuery();

            out.println("<h2 style='text-align:center;'>Vehicle Loan Customers</h2>");

            out.println("<table border='1' cellspacing='0' cellpadding='10' style='width:100%; text-align:center;'>");

            out.println("<tr style='background:#f2c94c;'>"
                    + "<th>Name</th>"
                    + "<th>Father/Husband</th>"
                    + "<th>Mobile</th>"
                    + "<th>Email</th>"
                    + "<th>Adhaar</th>"
                    + "<th>Vehicle</th>"
                    + "<th>Model</th>"
                    + "<th>Price</th>"
                    + "<th>Dealer</th>"
                    + "<th>Loan Type</th>"
                    + "<th>DownPayment</th>"
                    + "<th>Loan Amount</th>"
                    + "<th>Duration</th>"
                    + "<th>Address</th>"
                    + "</tr>");

            while (rs.next()) {

                out.println("<tr>"
                        + "<td>" + rs.getString("NAME") + "</td>"
                        + "<td>" + rs.getString("FATHER") + "</td>"
                        + "<td>" + rs.getString("MOBILE") + "</td>"
                        + "<td>" + rs.getString("ADHAAR") + "</td>"
                       + "<td>" + rs.getString("VEHICLETYPE") + "</td>"
                    + "<td>" + rs.getString("MODEL") + "</td>"
                   + "<td>" + rs.getString("PRICE") + "</td>"
                	+"<td>" + rs.getString("DEALER") + "</td>"
                	 + "<td>" + rs.getString("LOANTYPE") + "</td>"
                			 + "<td>" + rs.getString("DOWNPAYMENT") + "</td>"	 
                     + "<td>" + rs.getString("LOANAMOUNT") + "</td>"
                    		 + "<td>" + rs.getString("DURATION") + "</td>"
                    				 + "<td>" + rs.getString("ADDRESS") + "</td>"
 
                        + "</tr>");
            }

            out.println("</table>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }
}