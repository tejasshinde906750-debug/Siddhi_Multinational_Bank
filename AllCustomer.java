package com.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/ViewCustomersServlet")
public class AllCustomer extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        Connection con = null;

        try {
            con = DBConnect.getConnection();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM Bank_Account ORDER BY ACCOUNT_NO DESC");
            ResultSet rs = ps.executeQuery();

          
            out.println("<html><head>");
            out.println("<title>All Customers</title>");

           
            out.println("<style>");
            out.println("table { border-collapse: collapse; width: 100%; }");
            out.println("th, td { border: 1px solid black; padding: 8px; text-align: center; }");
            out.println("th { background-color: #333; color: white; }");
            out.println("tr:nth-child(even) { background-color: #f2f2f2; }");
            out.println("</style>");

            out.println("</head><body>");

            out.println("<h2>All Bank Customers</h2>");

      
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Account No</th>");
            out.println("<th>Name</th>");
            out.println("<th>Type</th>");
            out.println("<th>Mobile</th>");
            out.println("<th>Email</th>");
          //  out.println("<th>Balance</th>");
            out.println("</tr>");

            while (rs.next()) {
                out.println("<tr>");
               out.println("<td>" + rs.getInt("ACCOUNT_NO") + "</td>");
                out.println("<td>" + rs.getString("NAME") + "</td>");
                out.println("<td>" + rs.getString("ACCOUNT_TYPE") + "</td>");
                out.println("<td>" + rs.getString("MOBILE") + "</td>");
                out.println("<td>" + rs.getString("EMAIL") + "</td>");
               // out.println("<td>" + rs.getDouble("BALANCE") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</body></html>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3 style='color:red;'>Error loading customers</h3>");
        } finally {
            try {
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}