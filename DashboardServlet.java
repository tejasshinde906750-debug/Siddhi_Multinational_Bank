package com.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        Connection con = null;

        try {
            con = DBConnect.getConnection();

            double total = 0;

            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT NVL(SUM(BALANCE),0) AS TOTAL FROM Bank_Account");
                 ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    total = rs.getDouble("TOTAL");
                }
            }

            out.println("<html><head><title>Dashboard</title>");
            out.println("<style>");

            out.println("body{margin:0;font-family:Arial;background:linear-gradient(to right,#1e3c72,#2a5298);}");    
            out.println(".header{background:#0f2027;color:white;padding:15px;text-align:center;font-size:24px;}");    
            out.println(".card{width:400px;margin:100px auto;background:white;padding:30px;border-radius:12px;text-align:center;box-shadow:0px 5px 20px rgba(0,0,0,0.3);}");    
            out.println(".amount{font-size:32px;color:green;margin-top:20px;font-weight:bold;}");    

            out.println("</style></head><body>");

            out.println("<div class='header'> Bank Dashboard</div>");

            out.println("<div class='card'>");
            out.println("<h2>Total Bank Balance</h2>");
            out.println("<div class='amount'>₹ " + total + "</div>");
            out.println("</div>");

            out.println("</body></html>");

        } catch (Exception e) {
            e.printStackTrace(); 
            out.println("<h3 style='color:red;'>Something went wrong</h3>");
        } finally {
            try {
                if (con != null) con.close(); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}