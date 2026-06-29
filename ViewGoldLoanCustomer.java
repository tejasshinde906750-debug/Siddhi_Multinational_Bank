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

@WebServlet("/ViewGoldLoanCustomer")
public class ViewGoldLoanCustomer extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        try {
            Connection con = DBConnect.getConnection();

            PreparedStatement pmst = con.prepareStatement("SELECT * FROM GOLDLOAN");
            ResultSet rs = pmst.executeQuery();

            out.println("<h2 style='text-align:center;'>Gold Loan Customers</h2>");

            out.println("<table border='1' cellspacing='0' cellpadding='10' style='width:100%; text-align:center;'>");

            out.println("<tr style='background:#f2c94c;'>"
                    + "<th>Name</th>"
                    + "<th>Husband</th>"
                    + "<th>Mobile</th>"
                    + "<th>Adhaar</th>"
                    + "<th>PAN</th>"
                    + "<th>Address</th>"
                    + "<th>DOB</th>"
                    + "<th>Income</th>"
                    + "<th>Things</th>"
                    + "<th>Weight</th>"
                    + "<th>Purity</th>"
                    + "<th>Loan Amount</th>"
                    + "<th>Duration</th>"
                    + "<th>Loan Type</th>"
                    + "</tr>");

            while (rs.next()) {

                out.println("<tr>"
                        + "<td>" + rs.getString("NAME") + "</td>"
                        + "<td>" + rs.getString("HUSBAND") + "</td>"
                        + "<td>" + rs.getString("MOBILE") + "</td>"
                        + "<td>" + rs.getString("PAN") + "</td>"
                        + "<td>" + rs.getString("ADDRESS") + "</td>"
                        + "<td>" + rs.getString("ADHAAR") + "</td>"
                        + "<td>" + rs.getString("DOB") + "</td>"
                        + "<td>" + rs.getString("INCOME") + "</td>"
                        + "<td>" + rs.getString("THINGS") + "</td>"
                        + "<td>" + rs.getString("WEIGHT") + "</td>"
                        + "<td>" + rs.getString("PURITY") + "</td>"
                        + "<td>" + rs.getString("LOANAMOUNT") + "</td>"
                        + "<td>" + rs.getString("DURATION") + "</td>"
                        + "<td>" + rs.getString("LOANTYPE") + "</td>"
                        + "</tr>");
            }

            out.println("</table>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }
}		