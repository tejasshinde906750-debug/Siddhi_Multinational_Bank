package com.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/TodaysTransaction")
public class TodaysTransaction extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        String date = req.getParameter("date");

        try {
            Connection con = DBConnect.getConnection();

            PreparedStatement pmst = con.prepareStatement(
                "SELECT * FROM Transactions WHERE TRUNC(TXN_DATE) = ? "
            );

            pmst.setDate(1, java.sql.Date.valueOf(date));

            ResultSet rs = pmst.executeQuery();


            out.println("<html>");
            out.println("<head>");
            out.println("<title>Today's Transactions</title>");

        
            out.println("<style>");

            out.println("body { font-family: Arial; background:#f4f6f9; padding:20px; }");

            out.println("h2 { text-align:center; color:#2c3e50; margin-bottom:20px; }");

            out.println(".container { width:90%; margin:auto; }");

            out.println("table { width:100%; border-collapse:collapse; background:white; "
                    + "box-shadow:0 4px 10px rgba(0,0,0,0.1); border-radius:8px; overflow:hidden; }");

            out.println("th { background:#3498db; color:white; padding:12px; }");

            out.println("td { padding:10px; text-align:center; border-bottom:1px solid #ddd; }");

            out.println("tr:hover { background:#f1f1f1; }");

       
            out.println(".credit { color:green; font-weight:bold; }");
            out.println(".debit { color:red; font-weight:bold; }");

            out.println("</style>");

            out.println("</head>");
            out.println("<body>");

            out.println("<div class='container'>");
            out.println("<h2>Today's Transactions</h2>");

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Acc No</th>");
            out.println("<th>Type</th>");
            out.println("<th>Amount</th>");
            out.println("<th>Date</th>");
            out.println("</tr>");

            while (rs.next()) {

                String type = rs.getString("TXN_TYPE");

              
                String cssClass = "";

                if (type.equalsIgnoreCase("CREDIT")) {
                    cssClass = "credit";
                } else if (type.equalsIgnoreCase("DEBIT")) {
                    cssClass = "debit";
                }

                out.println("<tr>");
                out.println("<td>" + rs.getInt("TXN_ID") + "</td>");
                out.println("<td>" + rs.getInt("ACCOUNT_NO") + "</td>");
                out.println("<td class='" + cssClass + "'>" + type + "</td>");
                out.println("<td>" + rs.getDouble("AMOUNT") + "</td>");
                out.println("<td>" + rs.getDate("TXN_DATE") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</div>");

            out.println("</body>");
            out.println("</html>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3 style='color:red;text-align:center;'>Error: " + e.getMessage() + "</h3>");
        }
    }
}