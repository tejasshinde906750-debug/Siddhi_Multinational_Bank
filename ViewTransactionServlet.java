package com.backend;

import java.io.*;
import java.sql.*;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/ViewTransactionServlet")
public class ViewTransactionServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        String accno = req.getParameter("Account_No");

        if (accno == null || accno.isEmpty()) {
            out.println("<h3>Account Number Missing</h3>");
            return;
        }

        try {
            Connection con = DBConnect.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM Transactions WHERE ACCOUNT_NO=? ORDER BY TXN_DATE DESC"
            );

            ps.setInt(1, Integer.parseInt(accno));

            ResultSet rs = ps.executeQuery();

            out.println("<html><head><title>Transactions</title>");

            out.println("<style>");
            out.println("body{font-family:Arial;background:linear-gradient(to right,#1e3c72,#2a5298);text-align:center;}");
            out.println(".card{background:white;width:750px;margin:80px auto;padding:20px;border-radius:10px;}");
            out.println("table{width:100%;border-collapse:collapse;}");
            out.println("th,td{padding:10px;border-bottom:1px solid #ccc;}");
            out.println("th{background:#2a5298;color:white;}");

            out.println(".credit{color:green;font-weight:bold;}");
            out.println(".debit{color:red;font-weight:bold;}");

            out.println("</style></head><body>");

            out.println("<div class='card'>");
            out.println("<h2>Transaction History</h2>");

            out.println("<table>");
            out.println("<tr><th>ID</th><th>Type</th><th>Amount</th><th>Date</th></tr>");

            boolean found = false;

            while (rs.next()) {
                found = true;

                String type = rs.getString("TXN_TYPE");

                String cssClass = "";

               
                if ("CREDIT".equalsIgnoreCase(type) || "DEPOSIT".equalsIgnoreCase(type)) {
                    cssClass = "credit";
                } else if ("DEBIT".equalsIgnoreCase(type) || "WITHDRAW".equalsIgnoreCase(type)) {
                    cssClass = "debit";
                }

                out.println("<tr>");
                out.println("<td>" + rs.getInt("TXN_ID") + "</td>");
                out.println("<td class='" + cssClass + "'>" + type + "</td>");
                out.println("<td>₹ " + rs.getDouble("AMOUNT") + "</td>");
                out.println("<td>" + rs.getDate("TXN_DATE") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            if (!found) {
                out.println("<h3>No Transactions Found</h3>");
            }

            out.println("</div></body></html>");

        } catch (NumberFormatException e) {
            out.println("<h3>Invalid Account Number</h3>");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }
}